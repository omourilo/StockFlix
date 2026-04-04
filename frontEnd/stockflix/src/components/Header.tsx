import { Menu } from 'lucide-react'
function Header() {

  return (
    <>
       <section className="w-screen top-0 z-50 flex items-center p-8 h-18 border-b sticky bg-white border-(--borderColor)">
            <div className="flex gap-6 items-center">
                <span className="flex items-center justify-center cursor-pointer before:content-['']  before:absolute  before:m-auto before:flex before:items-center before:justify-center before:w-0 before:h-0 before:rounded-full before:bg-zinc-300 before:z-[-1] hover:before:w-8 hover:before:h-8"> 
                    <Menu size={20}/>
                </span>
                <p className="font-bold text-xl"> stockflix </p>
            </div>
       </section>
    </>
  )
}

export default Header