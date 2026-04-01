import Logo from '../assets/logo.svg'

function Login() {
  
  return (
    <>
       <section className="w-screen h-screen flex">
            <section className="h-screen bg-blue-800 w-1/2">
                <div className="relative h-full w-full bg-slate-950 overflow-hidden">
                    <div className="p-8">
                        <h2 className="text-zinc-50 text-8xl font-bold">StockFlix</h2>
                        <p className="pt-5 text-xl text-zinc-500">sdadasdsadasf</p>
                    </div>
                    <div className="absolute -top-[10%] -left-[10%] h-120 w-150 rounded-full bg-[radial-gradient(circle_at_center,rgba(30,58,138,0.3),transparent_70%)] blur-[80px]">
                    </div>

                    <div className="absolute -bottom-[20%] -right-[10%] h-125 w-125 rounded-full bg-[radial-gradient(circle_at_center,rgba(16,185,129,0.1),transparent_70%)] blur-[100px]">
                    </div>

                    <div className="absolute inset-0 bg-[linear-gradient(to_right,#80808012_1px,transparent_1px),linear-gradient(to_bottom,#80808012_1px,transparent_1px)] bg-[size:24px_24px]">
                    </div>
                    </div>
            </section>
            <section className="h-screen w-1/2 flex justify-center items-center bg-white">
  <div className="w-full max-w-md p-8 space-y-8 bg-white rounded-2xl border border-slate-200">
    

    <div className="text-center flex items-center justify-center flex-col transition space-y-2">
      <img src= {Logo} alt="logo" className='w-32'/>
      <h2 className="text-3xl font-bold text-slate-900">Acesse o Sistema</h2>
      <p className="text-slate-500 text-sm">Entre com suas credenciais de estoque</p>
    </div>

    <form className="space-y-6">
      <div className="space-y-1">
        <label className="text-sm font-medium text-slate-700 ml-1">Usuário</label>
        <input type="text" placeholder="ex: eduardo.estoque" className="w-full px-4 py-3 rounded-xl border border-slate-200 bg-slate-50 text-slate-900 focus:outline-none focus:ring-2 focus:ring-blue-500/20 focus:border-blue-500 transition-all placeholder:text-slate-400"/>
      </div>

      <div className="space-y-1">
        <label className="text-sm font-medium text-slate-700 ml-1">Senha</label>
        <input type="password" placeholder="••••••••" className="w-full px-4 py-3 rounded-xl border border-slate-200 bg-slate-50 text-slate-900 focus:outline-none focus:ring-2 focus:ring-blue-500/20 focus:border-blue-500 transition-all placeholder:text-slate-400" />
      </div>

      <button type="submit" className="w-full py-3 px-4 bg-violet-700 hover:bg-violet-900 text-white font-semibold rounded-xl shadow-md hover:shadow-lg transform active:scale-[0.98] transition-all cursor-pointer">
        Entrar no Painel
      </button>
    </form>

    <p className="text-center text-xs text-slate-400">
      Problemas com acesso? <span className="text-blue-500 cursor-pointer hover:underline">Suporte TI</span>
    </p>
  </div>
</section>
       </section>
    </>
  )
}

export default Login