import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'

import {createBrowserRouter, RouterProvider} from 'react-router-dom'
import Login from './Pages/Login.tsx'
import Home from  './Pages/Home.tsx'
import Error from  './Pages/Error.tsx'
import Create from './Pages/Create.tsx'
import History from './Pages/History.tsx'
import Dashboard from './Pages/Dashboard.tsx'
import ProductDetail from './Pages/ProductDetail.tsx'


const router = createBrowserRouter([
  {
    path:"/Login",
    element: <Login/>
  },
  {
    path:"/Create",
    element:<Create/>
  },
  {
    path:"/History",
    element:<History/>
  },
  {
    path:"/Products",
    element:<Home/>,
  },
  {
    path:"/Products/:id",
    element:<ProductDetail/>
  },
  {
    path:"/",
    element:<Dashboard/>,
    errorElement:<Error/>
  }
])


createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <RouterProvider router={router}/>
  </StrictMode>,
)
