import { LayoutDashboard, FileText, Tags, Settings, LogOut } from 'lucide-react';

const Sidebar = () => {
  const menuItems = [
    { icon: <LayoutDashboard size={20} />, label: 'Dashboard' },
    { icon: <FileText size={20} />, label: 'Posts' },
    { icon: <Tags size={20} />, label: 'Categories' },
    { icon: <Settings size={20} />, label: 'Settings' },
  ];

  return (
    <aside className="h-screen w-64 bg-(--sideBarBackground) text-gray-100 flex flex-col border-r border-white/10 sticky top-0">
      {/* Logo / Header da Sidebar */}
      <div className="p-6 text-xl font-bold border-b border-white/10">
        Stock<span className="text-blue-500">Flix</span>
      </div>

      {/* Navegação Principal */}
      <nav className="flex-1 p-4 overflow-y-auto custom-scrollbar">
        <ul className="space-y-2">
          {menuItems.map((item, index) => (
            <li key={index}>
              <a
                href="#"
                className="flex items-center gap-3 p-3 rounded-lg transition-all duration-200 hover:bg-white/10 hover:text-white group"
              >
                <span className="text-gray-400 group-hover:text-blue-400">
                  {item.icon}
                </span>
                <span className="font-medium">{item.label}</span>
              </a>
            </li>
          ))}
        </ul>
      </nav>

      {/* Footer da Sidebar */}
      <div className="p-4 border-t border-white/10">
        <button className="flex items-center gap-3 p-3 w-full rounded-lg hover:bg-red-500/10 hover:text-red-500 transition-colors">
          <LogOut size={20} />
          <span className="font-medium">Sair</span>
        </button>
      </div>
    </aside>
  );
};

export default Sidebar;