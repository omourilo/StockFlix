import { useParams } from "react-router-dom";
import Header from '../components/Header.tsx'
import Sidebar from '../components/Sidebar.tsx'
import Footer from '../components/Footer.tsx'
import {useState} from 'react'

const ProductDetail = () =>{
    const { id } = useParams<{ id: string }>();
    const [sidebarOpen, setsidebarOpen] = useState(true)
    return(
        <> 
        <div className="flex flex-col min-h-screen">
            <Header onMenuClick={() => setsidebarOpen(!sidebarOpen)}/>
            <Sidebar isOpen={sidebarOpen}/>
            <main className='h-full flex-1'>
                <section className={`${sidebarOpen ? 'ml-64': 'ml-0'} transition-all duration-300 p-6`}>
                    Exibindo detalhes do produto: {id} 
                </section>
            </main>
            <Footer/>
        </div>
        </>
    )
}

export default ProductDetail