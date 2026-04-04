
function Header() {

  return (
    <>
       <section className="w-screen top-0 z-50 flex items-center p-8 h-18 border-b sticky border-(--borderColor)">
            <div className="flex gap-6 items-center">
                <span className="before:content-none before:bg-zinc-400"> / </span>
                <p className="font-bold text-xl"> stockflix </p>
            </div>
       </section>
    </>
  )
}

export default Header