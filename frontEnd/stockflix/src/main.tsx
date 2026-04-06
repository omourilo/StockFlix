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
import Movement from './Pages/Movement.tsx'
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
    path:"/Movement",
    element:<Movement/>
  },
  {
    path:"/",
    element:<Home/>,
    errorElement:<Error/>
  },
  {
    path:"/:id",
    element:<ProductDetail/>
  }
])


createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <RouterProvider router={router}/>
  </StrictMode>,
)
