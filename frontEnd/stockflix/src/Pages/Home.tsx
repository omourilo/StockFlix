import Header from '../components/Header.tsx'
import Sidebar from '../components/Sidebar.tsx'
import Footer from '../components/Footer.tsx'
import { Search } from 'lucide-react'
import { Link } from 'react-router-dom'


function Home() {
  interface Produto{
    id:number;
    nome:string;
    preco:string;
    quantidade:number;
  }

  const produtos: Produto[] = [
    { id: 101, nome: "Smartphone Galaxy", preco: "R$ 2.500,00", quantidade: 15 },
    { id: 102, nome: "Notebook Pro", preco: "R$ 5.800,00", quantidade: 8 },
    { id: 103, nome: "Fone Bluetooth", preco: "R$ 290,00", quantidade: 42 },
  ];

  return (
    <>
      <div className="flex flex-col min-h-screen">
       <Header/>
       <Sidebar/>
       <main className='h-full flex-1'>
          <section className='ml-64 p-6'>

            <div className="flex flex-col gap-6 ">

              <div className="flex items-baseline gap-3">
                <h1 className="text-3xl font-bold text-gray-800 tracking-tight">Estoque</h1>
                <span className="text-sm font-medium text-gray-500 px-2 py-0.5">
                  10 itens cadastrados
                </span>
              </div>

                <div className="flex flex-wrap items-center justify-between gap-3">
                  <div className="relative flex-1 max-w-sm">
                    <button className='absolute right-0 top-1/2 -translate-y-1/2 flex w-10 items-center justify-center cursor-pointer h-full'>
                      <Search size={20}/>
                    </button>
                    <input type="text" placeholder="Buscar item ou código..." className="w-full relative pl-4 pr-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"/>
                  </div>
                  
                  <button className="flex items-center gap-2 bg-green-600 cursor-pointer hover:bg-green-700 text-white font-semibold py-2 px-6 rounded-lg ">
                    <span className="text-lg">+</span>
                    Adicionar produto
                  </button>
                </div>
              </div>
            <div>
              <div className="mt-4 border border-gray-200">
                <table className="min-w-full divide-y divide-gray-200 bg-white text-sm">
                      <thead className="bg-gray-50">
                        <tr>
                          <th className="px-4 py-3 text-left font-semibold text-gray-900">Produto</th>
                          <th className="px-4 py-3 text-left font-semibold text-gray-900">ID</th>
                          <th className="px-4 py-3 text-left font-semibold text-gray-900">Preço</th>
                          <th className="px-4 py-3 text-left font-semibold text-gray-900">Qtd.</th>
                          <th className="px-4 py-3 text-right font-semibold text-gray-900">Ações</th>
                        </tr>
                      </thead>

                      <tbody className="divide-y divide-gray-200">
                        {produtos.map((item) => (
                          <tr key={item.id} className="hover:bg-gray-50 transition-colors">
                            <td className="px-4 py-3 font-medium text-gray-900">{item.nome}</td>
                            <td className="px-4 py-3 text-gray-700">{item.id}</td>
                            <td className="px-4 py-3 text-gray-700">{item.preco}</td>
                            <td className="px-4 py-3 text-gray-700">{item.quantidade}</td>
                            <td className="px-4 py-3 text-right">
                              <Link to={`/produtos/${item.id}`} className="text-blue-600 hover:text-blue-800 font-medium underline">
                                Ver detalhes
                              </Link>
                            </td>
                          </tr>
                        ))}
                      </tbody>
                    </table>

              </div>
            </div>
          </section>
       </main>
       <Footer/>
      </div>
    </>
  )
}

export default Home