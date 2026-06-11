# StockFlix — Documentação Técnica do Backend

> **Versão:** 0.0.1-SNAPSHOT  
> **Stack:** Java 21 · Spring Boot 4.0.3 · MySQL · Railway  
> **Última atualização:** Junho 2026

---

## Índice

1. [Visão Geral](#1-visão-geral)
2. [Arquitetura e Estrutura de Pacotes](#2-arquitetura-e-estrutura-de-pacotes)
3. [Configuração e Variáveis de Ambiente](#3-configuração-e-variáveis-de-ambiente)
4. [Modelo de Dados](#4-modelo-de-dados)
5. [Módulos do Sistema](#5-módulos-do-sistema)
   - [Auth](#51-auth)
   - [Usuario](#52-usuario)
   - [Estoque](#53-estoque)
   - [Setor](#54-setor)
   - [Produto](#55-produto)
   - [Movimentacao](#56-movimentacao)
   - [Previsao](#57-previsao)
6. [Segurança](#6-segurança)
7. [Tratamento de Exceções](#7-tratamento-de-exceções)
8. [Soft Delete — Padrão de Ativação/Desativação](#8-soft-delete--padrão-de-ativaçãodesativação)
9. [Algoritmo de Previsão de Demanda](#9-algoritmo-de-previsão-de-demanda)
10. [Referência de Endpoints](#10-referência-de-endpoints)
11. [Testes](#11-testes)
12. [Dependências](#12-dependências)
13. [Como Adicionar um Novo Módulo](#13-como-adicionar-um-novo-módulo)

---

## 1. Visão Geral

O StockFlix é um sistema de **gestão de estoque com previsão de demanda**. O backend é uma API REST stateless, autenticada via JWT em cookie HttpOnly, com banco de dados MySQL gerenciado pelo Hibernate (DDL automático).

**Características principais:**
- Autenticação JWT via cookie (sem localStorage no cliente)
- Dois níveis de acesso: `ADMIN` e `COMUM`
- Soft delete em todas as entidades principais (campo `ativo`)
- Previsão de demanda por Média Móvel Ponderada
- Deploy no Railway com variáveis de ambiente protegidas

---

## 2. Arquitetura e Estrutura de Pacotes

O projeto segue a arquitetura em camadas padrão do Spring Boot, com cada domínio isolado em seu próprio pacote.

```
src/main/java/com/stockFlix/
│
├── FlixApplication.java          # Entrypoint — carrega .env antes do Spring
│
├── auth/                         # Autenticação e geração de token
│   ├── AuthController.java
│   ├── AuthService.java
│   ├── JwtUtil.java
│   ├── LoginDTO.java
│   └── LoginResponseDTO.java
│
├── config/                       # Configuração de segurança
│   ├── SecurityConfig.java       # Regras de autorização, CORS, filtros
│   └── JwtFilter.java            # Intercepta requisições e valida JWT
│
├── usuario/                      # Domínio de usuários
│   ├── Usuario.java              # Entidade JPA + implementa UserDetails
│   ├── UsuarioController.java
│   ├── UsuarioService.java
│   ├── UsuarioRepository.java
│   ├── UsuarioDTO.java
│   └── UsuarioDetailsService.java # Ponte entre o Spring Security e o banco
│
├── estoque/                      # Domínio de estoques
├── setor/                        # Domínio de setores (pertence a estoque)
├── produto/                      # Domínio de produtos (pertence a setor)
├── movimentacao/                 # Domínio de movimentações de estoque
├── previsao/                     # Domínio de previsão de demanda
│
└── excecoes/                     # Exceções customizadas + handler global
    ├── GlobalExceptionHandler.java
    ├── NotFoundException.java
    ├── DisabledEntityException.java
    ├── InsufficientStockException.java
    ├── LoginAlreadyExistsException.java
    └── PopulatedDeleteException.java
```

### Fluxo de uma requisição típica

```
HTTP Request
    └─► JwtFilter           (valida cookie JWT, popula SecurityContext)
            └─► Controller  (recebe DTO, delega ao Service)
                    └─► Service  (regras de negócio, orquestra Repositories)
                            └─► Repository  (JPA — acesso ao banco)
                                    └─► MySQL
```

### Por que cada domínio tem seus próprios arquivos?

Cada pacote contém Entity, Controller, Service, Repository e DTO juntos — isso é chamado de **package by feature**. A vantagem é que tudo relacionado a `produto`, por exemplo, está em um lugar só. Facilita manutenção, leitura e adição de features sem precisar navegar por múltiplas pastas.

---

## 3. Configuração e Variáveis de Ambiente

O arquivo `application.properties` não contém segredos diretamente — ele referencia variáveis de ambiente com a sintaxe `${NOME_VARIAVEL}`.

Em desenvolvimento local, essas variáveis são carregadas de um arquivo `.env` na raiz do projeto pelo `dotenv-java`, **antes** que o Spring inicialize (ver `FlixApplication.java`).

Em produção (Railway), as variáveis são configuradas diretamente no painel da plataforma.

### Variáveis necessárias

| Variável              | Descrição                                        | Exemplo                          |
|-----------------------|--------------------------------------------------|----------------------------------|
| `DB_URL`              | URL JDBC de conexão com o MySQL                  | `jdbc:mysql://host:3306/db_name` |
| `DB_USERNAME`         | Usuário do banco de dados                        | `root`                           |
| `DB_PASSWORD`         | Senha do banco de dados                          | `senha_segura`                   |
| `JWT_SECRET`          | Chave secreta para assinar os tokens JWT (Base64)| `base64_encoded_256bit_key`      |
| `CORS_ALLOWED_ORIGIN` | Origem do frontend autorizada pelo CORS          | `https://meu-front.vercel.app`   |

> ⚠️ **Nunca commite o arquivo `.env` no Git.** Ele já está no `.gitignore`.

### Como o `.env` é carregado

```java
// FlixApplication.java
Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
dotenv.entries().forEach(e -> System.setProperty(e.getKey(), e.getValue()));
```

`ignoreIfMissing()` faz com que a aplicação não falhe se o `.env` não existir (comportamento esperado em produção no Railway).

---

## 4. Modelo de Dados

### Hierarquia de entidades

```
Estoque  (1)
  └─► Setor  (N)
        └─► Produto  (N)
              ├─► Movimentacao  (N) ◄── Usuario (N)
              └─► Previsao  (N)
```

### Regra de cascade de soft delete

Quando uma entidade pai é desativada, todas as filhas também são desativadas em cascata:

```
Desativar Estoque  →  desativa todos os Setores filhos
                         →  desativa todos os Produtos de cada Setor
```

O mesmo vale para ativação. Isso é feito manualmente no `EstoqueService`, pois o cascade do JPA aqui é de persistência, não de lógica de negócio.

### Convenção do campo `ativo`

Todas as entidades principais têm um campo `Boolean ativo`:
- `true` → entidade ativa, visível e operável
- `false` → entidade inativa (soft delete — o registro permanece no banco)

**A responsabilidade de filtrar por `ativo` é do frontend**, que recebe o campo em todos os DTOs e decide o que exibir conforme o contexto da tela.

---

## 5. Módulos do Sistema

### 5.1 Auth

**Pacote:** `com.stockFlix.auth`

Responsável pelo login e pela restauração de sessão.

#### Endpoints

| Método | Rota        | Acesso       | Descrição                                      |
|--------|-------------|--------------|------------------------------------------------|
| POST   | `/auth/login` | Público     | Autentica o usuário e define o cookie JWT      |
| GET    | `/auth/me`    | Autenticado | Retorna os dados do usuário da sessão atual    |

#### Fluxo do login (`AuthService.login`)

1. Busca o usuário pelo login no banco
2. Verifica se o usuário está ativo (`ativo = true`)
3. Delega a validação de senha ao `AuthenticationManager` do Spring Security
4. Determina a role: `acessoADM = true` → `"ADMIN"`, caso contrário `"COMUM"`
5. Gera o token JWT via `JwtUtil.gerarToken(login, role)`
6. Cria um cookie `HttpOnly`, `Secure`, `SameSite=None` e adiciona ao `HttpServletResponse`
7. Retorna `LoginResponseDTO` com id, login, acessoADM e ativo

> **Por que SameSite=None?** O frontend (Vercel) e o backend (Railway) estão em origens diferentes. `SameSite=None` com `Secure=true` é o único modo de enviar o cookie em requisições cross-origin.

#### `JwtUtil`

Responsável por todas as operações com o token:

| Método          | Descrição                                           |
|-----------------|-----------------------------------------------------|
| `gerarToken`    | Cria o JWT com subject (login), role e expiração 8h |
| `extrairEmail`  | Lê o subject do token                               |
| `extrairTipo`   | Lê a claim `role` do token                          |
| `validarToken`  | Compara email e verifica expiração                  |

A chave secreta é lida de `System.getProperty("JWT_SECRET")` e decodificada de Base64. A expiração está fixada em 8 horas (`1000 * 60 * 60 * 8` ms).

---

### 5.2 Usuario

**Pacote:** `com.stockFlix.usuario`

A entidade `Usuario` implementa `UserDetails` do Spring Security, tornando-a diretamente utilizável no processo de autenticação sem precisar de uma classe wrapper.

#### Campos da entidade

| Campo       | Tipo    | Descrição                                |
|-------------|---------|------------------------------------------|
| `id`        | long    | PK auto-incremento                       |
| `login`     | String  | Email único — usado como username        |
| `senha`     | String  | Hash BCrypt da senha                     |
| `acessoADM` | Boolean | `true` = ADMIN, `false` = COMUM          |
| `ativo`     | Boolean | Soft delete                              |

#### `UsuarioDTO`

O campo `senha` tem `@JsonProperty(access = WRITE_ONLY)` — ou seja, ele é aceito na entrada (POST/PUT) mas **nunca** é enviado nas respostas. Isso evita vazar o hash da senha para o cliente.

#### `UsuarioDetailsService`

Implementa `UserDetailsService` do Spring Security. É chamado pelo `JwtFilter` a cada requisição autenticada para carregar os dados do usuário do banco. Também verifica se o usuário está ativo antes de autorizar.

#### Endpoints

| Método | Rota                      | Acesso | Descrição                  |
|--------|---------------------------|--------|----------------------------|
| GET    | `/usuarios`               | AUTH   | Lista todos os usuários    |
| GET    | `/usuarios/{id}`          | AUTH   | Busca usuário por ID       |
| POST   | `/usuarios`               | ADMIN  | Cria novo usuário          |
| PUT    | `/usuarios/{id}`          | ADMIN  | Atualiza usuário           |
| DELETE | `/usuarios/desativar/{id}`| ADMIN  | Desativa usuário           |
| PUT    | `/usuarios/ativar/{id}`   | ADMIN  | Ativa usuário              |

---

### 5.3 Estoque

**Pacote:** `com.stockFlix.estoque`

Representa o contêiner de nível mais alto da hierarquia. Um estoque contém setores.

#### Endpoints

| Método | Rota                       | Acesso | Descrição                                    |
|--------|----------------------------|--------|----------------------------------------------|
| GET    | `/estoques`                | AUTH   | Lista todos os estoques                      |
| GET    | `/estoques/{id}`           | AUTH   | Busca estoque por ID                         |
| POST   | `/estoques`                | ADMIN  | Cria novo estoque                            |
| PUT    | `/estoques/{id}`           | ADMIN  | Atualiza nome do estoque                     |
| DELETE | `/estoques/desativar/{id}` | ADMIN  | Desativa estoque + setores + produtos filhos |
| PUT    | `/estoques/ativar/{id}`    | ADMIN  | Ativa estoque + setores + produtos filhos    |

> **Atenção ao desativar/ativar:** o `EstoqueService` percorre todos os setores e produtos filhos manualmente para aplicar o soft delete em cascata.

---

### 5.4 Setor

**Pacote:** `com.stockFlix.setor`

Pertence a um `Estoque`. Contém `Produto`s. Ao criar um setor, deve-se informar o `estoqueId` — o setor só pode ser criado dentro de um estoque ativo.

#### Endpoints

| Método | Rota                      | Acesso  | Descrição                                   |
|--------|---------------------------|---------|---------------------------------------------|
| GET    | `/setores`                | Público | Lista todos os setores                      |
| GET    | `/setores/{id}`           | Público | Busca setor por ID                          |
| GET    | `/setores/estoque/{id}`   | Público | Lista setores de um estoque específico      |
| POST   | `/setores`                | ADMIN   | Cria setor dentro de um estoque ativo       |
| PUT    | `/setores/{id}`           | ADMIN   | Atualiza nome/estoque do setor              |
| DELETE | `/setores/desativar/{id}` | ADMIN   | Desativa setor + produtos filhos            |
| PUT    | `/setores/ativar/{id}`    | ADMIN   | Ativa setor + produtos filhos               |

> `GET /setores` é **público** (não requer autenticação) — decisão de design para facilitar o preenchimento de formulários no frontend.

---

### 5.5 Produto

**Pacote:** `com.stockFlix.produto`

Pertence a um `Setor`. Contém a quantidade atual em estoque, controlada via `Movimentacao`.

#### Campos relevantes da entidade

| Campo       | Tipo   | Regra                                               |
|-------------|--------|-----------------------------------------------------|
| `quantidade`| int    | Começa em 0. Nunca pode ficar negativo.             |
| `ativo`     | Boolean| Produto inativo bloqueia novas movimentações.       |

#### Métodos de domínio em `Produto.java`

```java
adicionarQuantidade(int qtd)  // quantidade += qtd
removerQuantidade(int qtd)    // lança InsufficientStockException se qtd > quantidade
```

Esses métodos estão **na entidade**, não no service — isso é lógica de domínio puro que não depende de infraestrutura.

#### Endpoints

| Método | Rota                       | Acesso | Descrição                            |
|--------|----------------------------|--------|--------------------------------------|
| GET    | `/produtos`                | AUTH   | Lista todos os produtos              |
| GET    | `/produtos/{id}`           | AUTH   | Busca produto por ID                 |
| GET    | `/produtos/setor/{id}`     | AUTH   | Lista produtos de um setor           |
| POST   | `/produtos`                | ADMIN  | Cria produto (setor deve estar ativo)|
| PUT    | `/produtos/{id}`           | ADMIN  | Atualiza produto                     |
| DELETE | `/produtos/desativar/{id}` | ADMIN  | Desativa produto                     |
| PUT    | `/produtos/ativar/{id}`    | ADMIN  | Ativa produto                        |

---

### 5.6 Movimentacao

**Pacote:** `com.stockFlix.movimentacao`

Registra entradas e saídas de estoque. **Toda alteração de quantidade de produto passa por aqui.**

#### Campo `tipoMovimentacao`

| Valor   | Significado              |
|---------|--------------------------|
| `true`  | Entrada (adição de qtd)  |
| `false` | Saída (remoção de qtd)   |

#### Fluxo de criação

1. Valida que produto e usuário existem e estão ativos
2. Associa produto e usuário à movimentação
3. Chama `produto.adicionarQuantidade()` ou `produto.removerQuantidade()` conforme o tipo
4. Salva produto e movimentação

#### Fluxo de exclusão (reversão)

Ao deletar uma movimentação, o sistema **reverte o efeito** no estoque do produto:
- Se era entrada (`true`) → remove a quantidade que havia sido adicionada
- Se era saída (`false`) → adiciona de volta a quantidade que havia sido removida

> Este é um comportamento crítico para a consistência dos dados. Não existe soft delete em movimentação — a exclusão é definitiva e sempre reverte o impacto no produto.

#### Endpoints

| Método | Rota                                     | Acesso | Descrição                              |
|--------|------------------------------------------|--------|----------------------------------------|
| POST   | `/movimentacoes`                         | AUTH   | Cria movimentação e atualiza produto   |
| GET    | `/movimentacoes`                         | AUTH   | Lista todas as movimentações           |
| GET    | `/movimentacoes/{id}`                    | AUTH   | Busca movimentação por ID              |
| GET    | `/movimentacoes/produto/{id}`            | AUTH   | Lista movimentações de um produto      |
| GET    | `/movimentacoes/usuario/{id}`            | AUTH   | Lista movimentações de um usuário      |
| GET    | `/movimentacoes/data/{dataInicial}-{dataFinal}` | AUTH | Filtra por período (formato `yyyy-MM-dd`) |
| DELETE | `/movimentacoes/{id}`                    | AUTH   | Deleta e reverte impacto no produto    |

---

### 5.7 Previsao

**Pacote:** `com.stockFlix.previsao`

Gera uma previsão de demanda para um produto em um período futuro, com base no histórico de saídas (movimentações de tipo `false`).

#### Campos da entidade

| Campo           | Tipo      | Descrição                            |
|-----------------|-----------|--------------------------------------|
| `qtdPrevista`   | int       | Calculado automaticamente pelo service |
| `inicioPeriodo` | LocalDate | Data de início do período analisado  |
| `fimPeriodo`    | LocalDate | Data de fim do período analisado     |
| `criadoEm`      | LocalDate | Data em que a previsão foi gerada    |

> `qtdPrevista` nunca é informado pelo cliente — é sempre calculado internamente. Ver seção 9 para detalhes do algoritmo.

#### Endpoints

| Método | Rota                        | Acesso | Descrição                             |
|--------|-----------------------------|--------|---------------------------------------|
| POST   | `/previsoes`                | ADMIN  | Cria previsão para produto no período |
| GET    | `/previsoes`                | AUTH   | Lista todas as previsões              |
| GET    | `/previsoes/{id}`           | AUTH   | Busca previsão por ID                 |
| GET    | `/previsoes/produto/{id}`   | AUTH   | Lista previsões de um produto         |
| DELETE | `/previsoes/{id}`           | ADMIN  | Deleta uma previsão                   |

---

## 6. Segurança

### Pipeline de autenticação

```
Request chega
    │
    ▼
JwtFilter.doFilterInternal()
    ├── Extrai cookie "jwt"
    ├── Extrai login do token (JwtUtil.extrairEmail)
    ├── Carrega usuário do banco (UsuarioDetailsService)
    ├── Valida token (JwtUtil.validarToken)
    └── Popula SecurityContextHolder com UsernamePasswordAuthenticationToken
```

Se nenhum cookie JWT estiver presente, a requisição continua sem autenticação — o Spring Security então bloqueia o acesso baseado nas regras do `SecurityConfig`.

### Regras de autorização (`SecurityConfig`)

| Rota / Método         | Requisito     |
|-----------------------|---------------|
| `OPTIONS /**`         | Público       |
| `POST /auth/login`    | Público       |
| `GET /auth/me`        | Autenticado   |
| `GET /setores/**`     | Público       |
| `POST/PUT/DELETE` em qualquer recurso | ADMIN |
| `GET` nos demais recursos | Autenticado |
| `/movimentacoes/**`   | Autenticado   |

### CORS

A origem permitida é configurada via variável de ambiente `CORS_ALLOWED_ORIGIN`. Apenas uma origem é aceita. Credenciais (`allowCredentials = true`) são habilitadas para permitir o envio do cookie.

### Senhas

Armazenadas com hash **BCrypt** via `PasswordEncoder`. A senha nunca é retornada ao cliente (campo `WRITE_ONLY` no DTO).

---

## 7. Tratamento de Exceções

O `GlobalExceptionHandler` (`@RestControllerAdvice`) intercepta exceções lançadas em qualquer camada e retorna respostas HTTP padronizadas.

| Exceção                      | HTTP Status | Quando é lançada                                    |
|------------------------------|-------------|-----------------------------------------------------|
| `NotFoundException`          | 404         | Recurso não encontrado no banco                     |
| `DisabledEntityException`    | 400         | Tentativa de operar em entidade inativa             |
| `InsufficientStockException` | 400         | Remoção de quantidade maior que o estoque atual     |
| `LoginAlreadyExistsException`| 400         | Tentativa de criar usuário com login já existente   |
| `PopulatedDeleteException`   | 400         | Tentativa de deletar entidade com filhos vinculados |

Todas herdam de `RuntimeException` e são **unchecked** — não precisam ser declaradas na assinatura dos métodos.

---

## 8. Soft Delete — Padrão de Ativação/Desativação

Nenhuma entidade principal é deletada do banco. Em vez disso, o campo `ativo` é alternado.

### Convenção de rotas

```
DELETE /recurso/desativar/{id}  →  ativo = false
PUT    /recurso/ativar/{id}     →  ativo = true
```

> Note que `desativar` usa o verbo HTTP `DELETE` por semântica REST, e `ativar` usa `PUT`.

### Regras de negócio

- Não é possível criar movimentações para produtos inativos
- Não é possível criar produtos em setores inativos
- Não é possível criar setores em estoques inativos
- Usuários inativos não conseguem se autenticar
- Desativar um nível pai cascateia para todos os filhos

---

## 9. Algoritmo de Previsão de Demanda

**Tipo:** Média Móvel Ponderada (Weighted Moving Average)

**Implementação:** `PrevisaoService.calculoPrevisao()`

### Como funciona

O algoritmo busca todas as **saídas** (movimentações com `tipoMovimentacao = false`) de um produto no período informado, ordena-as cronologicamente e aplica pesos crescentes — movimentações mais recentes têm maior influência no resultado.

### Fórmula

```
resultado = Σ(quantidade_i × peso_i) / Σ(peso_i)

onde peso_i = posição cronológica (1, 2, 3, ...)
```

### Exemplo

Saídas no período: `[10, 20, 30]` (do mais antigo para o mais recente)

```
soma ponderada  = (10×1) + (20×2) + (30×3) = 10 + 40 + 90 = 140
soma dos pesos  = 1 + 2 + 3 = 6
resultado       = 140 / 6 ≈ 23.3  →  arredondado para 23
```

### Edge cases tratados

- Se não houver movimentações no período → lança `NotFoundException`
- O resultado é arredondado via `Math.round()` pois `qtdPrevista` é `int`

---

## 10. Referência de Endpoints

### Resumo completo

| Domínio       | Base URL         |
|---------------|------------------|
| Auth          | `/auth`          |
| Usuario       | `/usuarios`      |
| Estoque       | `/estoques`      |
| Setor         | `/setores`       |
| Produto       | `/produtos`      |
| Movimentacao  | `/movimentacoes` |
| Previsao      | `/previsoes`     |

### Formato de datas

Todas as datas são trafegadas como `String` no formato ISO `yyyy-MM-dd` (ex: `"2025-05-27"`).

### Swagger UI

A documentação interativa está disponível em `/swagger-ui/index.html` quando a aplicação está rodando. A rota `/v3/api-docs/**` também é pública.

---

## 11. Testes

Os testes estão em `src/test/java/com/stockFlix/serviceTests/` e cobrem a camada de Service com testes unitários usando Mockito.

| Arquivo de Teste             | Service Testado        |
|------------------------------|------------------------|
| `EstoqueServiceTest.java`    | `EstoqueService`       |
| `MovimentacaoServiceTest.java`| `MovimentacaoService` |
| `PrevisaoServiceTest.java`   | `PrevisaoService`      |
| `ProdutoServiceTest.java`    | `ProdutoService`       |
| `SetorServiceTest.java`      | `SetorService`         |
| `UsuarioServiceTest.java`    | `UsuarioService`       |

### Padrão dos testes

Os testes mockam os repositórios com `@Mock` e injetam no service via `@InjectMocks`. Cada teste cobre cenários de sucesso e de exceção (ex: busca por ID inexistente deve lançar `NotFoundException`).

---

## 12. Dependências

| Dependência                       | Versão  | Finalidade                              |
|-----------------------------------|---------|-----------------------------------------|
| spring-boot-starter-web           | 4.0.3   | API REST                                |
| spring-boot-starter-data-jpa      | 4.0.3   | ORM com Hibernate                       |
| spring-boot-starter-security      | 4.0.5   | Autenticação e autorização              |
| mysql-connector-j                 | latest  | Driver JDBC MySQL                       |
| jjwt-api / jjwt-impl / jjwt-jackson | 0.12.5 | Geração e validação de JWT             |
| dotenv-java                       | 3.0.0   | Carregamento de `.env` local            |
| springdoc-openapi-starter-webmvc-ui | 3.0.2 | Swagger UI automático                   |
| lombok                            | latest  | Redução de boilerplate (getters/setters)|
| junit                             | 4.13.2  | Framework de testes unitários           |

---

## 13. Como Adicionar um Novo Módulo

Siga o padrão dos módulos existentes:

1. **Crie o pacote** `com.stockFlix/<nome_do_modulo>/`

2. **Entidade JPA** (`NomeModulo.java`)
   - Anote com `@Entity`, `@Table(name = "nome_tabela")`
   - Adicione `@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor` (Lombok)
   - Inclua o campo `ativo` se precisar de soft delete
   - Adicione construtor que recebe o próprio DTO

3. **DTO** (`NomeModuloDTO.java`)
   - Declare como `record`
   - Inclua construtor que recebe a entidade para facilitar o mapeamento

4. **Repository** (`NomeModuloRepository.java`)
   - Interface que estende `JpaRepository<Entidade, Long>`
   - Adicione query derivations conforme necessário (`findAllByXxx`)

5. **Service** (`NomeModuloService.java`)
   - Anote com `@Service`
   - Injete repositories via construtor (não `@Autowired`)
   - Regras de negócio ficam aqui — não no Controller
   - Use `@Transactional` nos métodos que fazem múltiplas operações no banco

6. **Controller** (`NomeModuloController.java`)
   - Anote com `@RestController` e `@RequestMapping("/nome-rota")`
   - Injete o Service via construtor
   - Retorne `ResponseEntity` com status HTTP semântico
   - Mantenha o Controller fino — sem lógica de negócio

7. **Registre as rotas no `SecurityConfig`**
   - Defina quais verbos HTTP requerem `ADMIN` e quais requerem apenas autenticação

8. **Escreva testes** em `src/test/.../serviceTests/NomeModuloServiceTest.java`
