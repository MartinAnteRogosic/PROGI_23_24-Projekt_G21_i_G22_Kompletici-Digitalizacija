import React,{useState} from 'react';

import './App.css';
import { Login } from "./Login";
import { Register } from "./Register";


function App() {
  const storedForm = localStorage.getItem('currentForm');
  const [currentForm, setCurrentForm] = useState(storedForm || 'login');

  const toggleForm = (formName) => {
    setCurrentForm(formName);
    localStorage.setItem('currentForm', formName);
  };

  return (
    <div className="App">
      {
        currentForm === "login" ? <Login onFormSwitch={toggleForm}/> : <Register onFormSwitch={toggleForm}/>
      }
      
    </div>
  );
}

export default App;
