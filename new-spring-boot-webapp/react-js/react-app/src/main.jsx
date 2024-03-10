import React from 'react'
import ReactDOM from 'react-dom/client'
//import {App} from './App.jsx'
//import './index.css'
import { ProductApp } from './components/ProductApp.jsx'

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
      {/* <App />  */}
  {/* <ProductApp title={'Lista de Productos'}/>  */}
  {/*  forma 2  */}
  <ProductApp title={{text: 'Lista de Productos'}}/>

  </React.StrictMode>,
)
