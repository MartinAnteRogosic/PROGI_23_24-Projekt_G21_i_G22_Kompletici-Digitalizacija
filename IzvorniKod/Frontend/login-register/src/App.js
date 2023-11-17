import React, { useState } from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import './App.css';
import { LoginRegisterView } from './LoginRegisterView';
import HomePage from './components/HomePage'; // Adjust the path to your HomePage component
import { userContext } from './userContext';

function App() {

  const [user, setUser] = useState(null);

  return (
    <div className='App'>
      <BrowserRouter>
      <userContext.Provider value={{ user: user, setUser: setUser }}>
        <Routes>
          <Route path = "/" element = { <LoginRegisterView /> }/>
          <Route path = "/home" element = { <HomePage /> }/>
        </Routes>
      </userContext.Provider>
      </BrowserRouter>
    </div>
  );
}

export default App;
