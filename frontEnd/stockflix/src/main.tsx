import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'

import {createBrowserRouter, RouterProvider} from 'react-router-dom'
import Login from './Pages/Login.tsx'
import Home from  './Pages/Home.tsx'


const router = createBrowserRouter([
  {
    path:"/Login",
    element: <Login/>
  },
  {
    path:"/",
    element:<Home/>
  }
])


createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <RouterProvider router={router}/>
  </StrictMode>,
)
