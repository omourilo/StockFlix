import Header from '../components/Header.tsx'
import Sidebar from '../components/Sidebar.tsx'
import Footer from '../components/Footer.tsx'


function Home() {
  

  return (
    <>
      <div className="flex flex-col min-h-screen">
       <Header/>
       <Sidebar/>
       <main className='h-full flex-1'>
        a
       </main>
       <Footer/>
      </div>
    </>
  )
}

export default Home