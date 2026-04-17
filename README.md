# StockFlix

Sistema desktop de gerenciamento de estoque com previsão de demanda e alertas automáticos.

> Projeto desenvolvido para a disciplina de Engenharia de Software — UNICESUMAR, Centro Universitário de Ponta Grossa.

## Sobre o Projeto

O StockFlix é um sistema desktop voltado ao controle e administração de estoques de comércios e estabelecimentos. Ele permite registrar entradas e saídas de produtos, gerar histórico de movimentações e, com base nesses dados, produzir previsões de demanda e alertas sobre riscos de desabastecimento.

## Objetivos

- Gerenciar o estoque de forma simples e intuitiva
- Registrar o histórico completo de entradas, saídas e atualizações
- Gerar relatórios e previsões de demanda com base no histórico
- Emitir alertas automáticos sobre riscos de esvaziamento de produtos

## Público-Alvo

- Donos de comércios e estabelecimentos
- Funcionários responsáveis pelo controle de estoque
- Gestores de estoque

## Funcionalidades

### Requisitos Funcionais

| ID | Descrição |
|----|-----------|
| RF001 | Listar estoque |
| RF002 | Cadastrar produto |
| RF003 | Adicionar produto |
| RF004 | Remover produto |
| RF005 | Atualizar estoque |
| RF006 | Gerar relatório |
| RF007 | Listar histórico de saídas e entradas |
| RF008 | Algoritmo de previsão baseado em histórico de eventos |
| RF009 | Criar alertas baseados na previsão |

### Requisitos Não Funcionais

| ID | Descrição |
|----|-----------|
| RNF001 | Segurança |
| RNF002 | Consistência de dados |
| RNF003 | Velocidade de acesso às informações |
| RNF004 | Backup automático |
| RNF005 | Interface intuitiva |
| RNF006 | Hierarquia de acessos |

## Perfis de Acesso

### Administrador (ADM)
- Cadastrar, adicionar e remover produtos
- Atualizar estoque
- Todas as permissões de Usuário

### Usuário (Funcionário)
- Registrar entradas e saídas de produtos
- Listar produtos em estoque
- Consultar histórico de movimentações
- Visualizar previsões de demanda

## Arquitetura e Modelagem

### Diagrama de Casos de Uso
O sistema possui três atores principais: ADM, Usuário e Sistema. O Sistema é responsável por gerar o histórico de movimentações, criar previsões com base nesse histórico e disparar alertas automáticos.

### Diagrama de Classes
As principais entidades são:

- Produto — nome, preço, quantidade, descrição
- Setor — agrupa produtos
- Estoque — vincula setores
- Movimentação — registra entradas/saídas com data e tipo
- Previsão — quantidade prevista, período de início e fim
- Usuário — credenciais de acesso e nível de permissão (ADM ou funcionário)

### Banco de Dados
Estrutura relacional com as tabelas: `Produto`, `Setor`, `Estoque`, `Previsão` e `Usuario`, conectadas por chaves primárias e estrangeiras, formando uma hierarquia organizada de armazenamento.

## Regras de Negócio

- Produtos devem ter obrigatoriamente um nome
- O nome do produto não pode conter caracteres especiais ou números
- A quantidade de um produto não pode ser nula ou negativa (mínimo: 0)

## Fluxo de Operação (Entrada/Saída)

```
Início
  └─► Selecionar Produto e Quantidade
        └─► Validar Dados
              ├─► [Inválido] → Exibir Mensagem de Erro → Fim
              └─► [Válido] → Atualizar Banco de Dados
                    ├─► [Estoque < Mínimo] → Criar Alerta de Estoque
                    └─► [Estoque OK] → Exibir Confirmação de Sucesso → Fim
```
## Status do Projeto

| Módulo | Status |
|--------|--------|
| Front-End | Avançado — próximo do estágio final |
| Back-End | Em desenvolvimento |
| Banco de Dados | Concluído (com autenticação e todas as entidades) |
| Integração Front-End / Back-End | Pendente |

## Equipe

| Papel | Nome |
|-------|------|
| Programador Front-End | Eduardo Henrique Rodrigues |
| Tester / QA / Documentador | João Pedro Kloster Tolentino |
| Product Owner / Analista de Requisitos | Murilo Arruda |
| Programador Back-End | Ramon Albini Vieira |

## Instituição

UNICESUMAR — Centro Universitário de Ponta Grossa  
Curso: Engenharia de Software  
Cidade: Ponta Grossa — PR  
Ano: 2026
