import React, { useState } from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import './App.css';
import { LoginRegisterView } from './LoginRegisterView';
import HomePage from './components/HomePage/HomePage'; // Adjust the path to your HomePage component
import RequestsPage from './components/Requests/RequestsPage';
import ArchivePage from './components/Archive/ArchivePage';
import HistoryPage from './components/HistoryPage/HistoryPage';
import { userContext } from './userContext';

function App() {

  const [user, setUser] = useState(null);
  const userinfo = JSON.parse(sessionStorage.getItem("user"));
  //const { user } = useContext(userContext);
  let loggeduser = undefined;
  if (userinfo) {
    loggeduser = {
      "firstName": userinfo.firstName,
      "lastName": userinfo.lastName,
      "role": userinfo.role
    };
  };
  

  const ProtectedRoute = ({ user, children }) => {
    if (!user) {
      return <Navigate to="/" replace />;
    }
  
    return children;
  };

  return (
    <div className='App'>
      <BrowserRouter>
      <userContext.Provider value={{ user: user, setUser: setUser }}>
        <Routes>
          <Route path = "/" element = { <LoginRegisterView /> }/>
          <Route path = "/home" element = { 
            <ProtectedRoute user={loggeduser}>
              <HomePage />
            </ProtectedRoute> }/>
          <Route path = "/requests" element = { 
            <ProtectedRoute user={loggeduser}>
              <RequestsPage />
            </ProtectedRoute> }/>
          <Route path = "/archive" element = { 
            <ProtectedRoute user={loggeduser}>
              <ArchivePage />
            </ProtectedRoute> }/>
          <Route path = "/history" element = { 
            <ProtectedRoute user={loggeduser}>
              <HistoryPage />
            </ProtectedRoute> }/>
        </Routes>
      </userContext.Provider>
      </BrowserRouter>
    </div>
  );
}

export default App;
