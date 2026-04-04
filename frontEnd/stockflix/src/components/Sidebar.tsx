import { LayoutDashboard, FileText, Tags, Settings, LogOut } from 'lucide-react';

const Sidebar = () => {
  const menuItems = [
    { icon: <LayoutDashboard size={20} />, label: 'Dashboard' },
    { icon: <FileText size={20} />, label: 'Posts' },
    { icon: <Tags size={20} />, label: 'Categories' },
    { icon: <Settings size={20} />, label: 'Settings' },
  ];

  return (
    <aside className="h-full w-64 bg-(--sideBarBackground) text-gray-100 flex flex-col border-r border-white/10 absolute top-0">
      {/* <div className="p-6 text-xl font-bold border-b border-white/10">
        Stock<span className="text-blue-500">Flix</span>
      </div> */}
    

  {/* Área de Navegação com Scroll Interno */}
  <nav className="flex-1 p-4 mt-18 overflow-y-auto custom-scrollbar">
    <ul className="space-y-2">
      {menuItems.map((item, index) => (
        <li key={index}>
          <a href="#" className="flex items-center gap-3 p-3 rounded-lg transition-all duration-200 hover:bg-white/10 hover:text-white group">
            <span className="text-gray-400 group-hover:text-blue-400">
              {item.icon}
            </span>
            <span className="font-medium">{item.label}</span>
          </a>
        </li>
      ))}
    </ul>
  </nav>

  {/* Botão de Sair SEMPRE no rodapé */}
  <div className="p-4 border-t border-white/10 bg-(--sideBarBackground)">
    <button className="flex items-center gap-3 p-3 w-full rounded-lg hover:bg-red-500/10 hover:text-red-500 transition-colors cursor-pointer">
      <LogOut size={20} />
      <span className="font-medium">Sair</span>
    </button>
  </div>
    </aside>
  );
};

export default Sidebar;