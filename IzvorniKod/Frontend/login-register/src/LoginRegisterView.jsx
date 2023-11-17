import { React, useState } from "react";
import { Login } from './components/Login';
import { Register } from './components/Register';

export function LoginRegisterView() {

    const storedForm = localStorage.getItem('currentForm');
    const [currentForm, setCurrentForm] = useState(storedForm || 'login');

    const toggleForm = (formName) => {
        setCurrentForm(formName);
        localStorage.setItem('currentForm', formName);
    };
    
    return(
        <div>
      {currentForm === 'login' ? ( <Login onFormSwitch={toggleForm} /> ) : (
        <Register onFormSwitch={toggleForm} />
      )}
    </div>
    )
}