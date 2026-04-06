function Movement(){
    return(
            <>
            <h2 className="text-zinc-700 font-medium text-lg mb-6">Movimentação</h2>
            <section className="flex flex-col gap-4">
                <section className="flex gap-4">
                        <button className="px-6 py-1.5 text-sm font-medium rounded-md text-white cursor-pointer bg-green-600 hover:bg-green-700">
                            entrada
                        </button>
                        <button className="px-6 py-1.5 text-sm font-medium rounded-md text-white cursor-pointer bg-red-600 hover:bg-red-700">
                            saída
                        </button>
                    </section>
    
                    <section>
                        <button className="w-9 h-9 border border-zinc-300 rounded-md text-zinc-600 hover:bg-zinc-50 transition-colors cursor-pointer">−</button>
                        <input type="number" min={1} placeholder="Qtd." className="w-24 h-9 border border-zinc-300 rounded-md px-3 text-sm text-center text-slate-800 focus:outline-none focus:border-zinc-500"/>
                        <button className="w-9 h-9 border border-zinc-300 rounded-md text-zinc-600 hover:bg-zinc-50 transition-colors cursor-pointer">+</button>
                        <span className="text-xs text-zinc-400 ml-1">un</span>
                    </section>
    
                    <section>
                        <select className="w-full md:w-80 h-9 border border-zinc-300 rounded-md px-3 text-sm text-slate-700 bg-white focus:outline-none focus:border-zinc-500">
                            <option value="">Compra de fornecedor</option>
                            <option value="">Devolução de cliente</option>
                            <option value="">Transferência interna</option>
                            <option value="">Ajuste de inventário</option>
                        </select>
                    </section>
                    <section>
                        <button className="w-fit px-6 py-2 rounded-md text-sm text-white cursor-pointer bg-zinc-900">
                            Registrar 
                        </button>
                    </section>
                    </section>
                </>
    )
}

    {/* <h2 className="text-zinc-700 font-medium text-lg mb-6">Movimentação</h2>
            <div className="flex rounded-md border border-zinc-200 overflow-hidden w-fit">
                <div className="flex items-col gap-6">
                    <span className="text-xs font-bold uppercase text-zinc-400 tracking-wider">Tipo de Operação</span>
                    <div className="flex rounded-lg border border-zinc-200 overflow-hidden w-fit bg-white p-1 ">
                        <button className="px-6 py-1.5 text-sm font-medium rounded-md bg-zinc-900 text-white cursor-pointer">
                            entrada
                        </button>
                        <button className="px-6 py-1.5 text-sm font-medium rounded-md bg-zinc-900 text-white cursor-pointer">
                            saída
                        </button>
                    </div>
                    <button className="w-9 h-9 border border-zinc-300 rounded-md text-zinc-600 hover:bg-zinc-50 transition-colors cursor-pointer">−</button>
                    <input type="number" min={1} placeholder="Qtd." className="w-24 h-9 border border-zinc-300 rounded-md px-3 text-sm text-center text-slate-800 focus:outline-none focus:border-zinc-500"/>
                    <button className="w-9 h-9 border border-zinc-300 rounded-md text-zinc-600 hover:bg-zinc-50 transition-colors cursor-pointer">+</button>
                    <span className="text-xs text-zinc-400 ml-1">un</span>
                    </div>
                    <select className="w-full md:w-80 h-9 border border-zinc-300 rounded-md px-3 text-sm text-slate-700 bg-white focus:outline-none focus:border-zinc-500">
                        <option value="">Selecione o motivo</option>
                    </select>
                    {<p className="text-xs text-red-600"></p>}
                    <button className="w-fit px-6 py-2 rounded-md text-sm text-white transition-colors cursor-pointer bg-red-600 hover:bg-red-700">
                        Registrar 
                    </button>
        </div> */}

export default Movement