import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import Modal from 'react-modal';
import "./Statistic.css";
import { API } from '../../api';

const UserInfo = ({ user, handleLogout, openModal }) => {
  return (
    <div className="user-info">
      <span className="username">NAME: {user.firstName + ' ' + user.lastName}</span>
      <span className="username">ROLE: {user.role}</span>
      <button onClick={handleLogout}>Log out</button>
      <button onClick={openModal}>Change password</button>
    </div>
  );
};


// prikaz statistike u obliku liste (mapiranje)
const EmployeeList = ({ allEmployees }) => {
  return (
    <div>
      {allEmployees.map((employee) => (
        <div key={employee.id}>{employee.firstName} {employee.lastName}</div>
      ))}
    </div>
  );
};

const Statistic = () => {
  const [allEmployees, setAllEmployees] = useState([]);
  const [userRole, setUserRole] = useState('');
  const userinfo = JSON.parse(sessionStorage.getItem("user"));
  const [authorizationError, setAuthorizationError] = useState(false);

  useEffect(() => {
    // Fetch user role from sessionStorage or API
    setUserRole(userinfo.role);
  }, [userinfo.role]);

  const user = {
    "id": userinfo.id,
    "firstName": userinfo.firstName,
    "lastName": userinfo.lastName,
    "role": userinfo.role
  };

  const config = {
    headers: {
        Authorization: "Bearer " + userinfo.accessToken,
        "Access-Control-Allow-Origin": "*",
    },
  };

  const customStyles = {
    content: {
      top: '50%',
      left: '50%',
      right: 'auto',
      bottom: 'auto',
      marginRight: '-50%',
      transform: 'translate(-50%, -50%)',
    },
  };

  const [oldPassword, setOldPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [passwordConfirmation, setPasswordConfirm] = useState('');
  const [modalOpen, setModalOpen] = useState(false);

  const handleLogout = () => {
    // Handle logout logic
  };

  const openModal = () => {
    setModalOpen(true);
  };

  const closeModal = () => {
    setModalOpen(false);
    setOldPassword('');
    setNewPassword('');
    setPasswordConfirm('');
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
  };


  // Trebalo bi dohvatiti ID usera koji je ulogiran
  const fetchUserStats = async () => {
    try {
      const response = await API.get(`/api/v1/employee-management/statistics-for-employee`, config);
      setAllEmployees([response.data]); 
      setAuthorizationError(false);
    } catch (error) {
      console.error('Error fetching user statistics:', error);
      setAuthorizationError(true);
    }
  };

  // Trebalo bi dohvatiti statistiku svih zaposlenika samo za direktora
  const fetchEmployeeStats = async () => {
    try {
      const response = await API.get(`/api/v1/employee-management/statistics`, config);
      setAllEmployees([response.data]); 
      setAuthorizationError(false);
    } catch (error) {
      console.error('Error fetching employee statistics:', error);
      setAuthorizationError(true);
    }
  };

  const handleEmployeeClick = () => {
    if (userRole === "Director") {
      const employeeId = prompt("Enter employee ID:");
      if (employeeId) {
        fetchEmployeeStats(employeeId);
      }
    } else {
      fetchUserStats();
    }
  };

  const handleAllEmployeesClick = async () => {
    try {
      if (userRole === "DIRECTOR") {
        const response = await API.get('/api/v1/employee-management/all-employees', config);
        console.log(response);
        setAllEmployees(response.data);
        setAuthorizationError(false);
      } else {
        setAllEmployees([]);
        setAuthorizationError(true);
      }
    } catch (error) {
      console.error('Error fetching all employees:', error);
    }
  };

  return (
    <div className="container">
      <div className="header">
        <UserInfo user={user} handleLogout={handleLogout} openModal={openModal} />

        <div className="center-links">
          <Link to={`/home`}>Home</Link>
          <Link to={`/statistic`}>Statistic</Link>
          <Link to={`/requests`}>Requests</Link>
          <Link to={`/archive`}>Archive</Link>
          <Link to={`/history`}>History</Link>
        </div>
      </div>

      <h1 className="statistic-header">Statistic</h1>

      <div className="button-bar">
        <button className="button" onClick={handleEmployeeClick}>
          {userRole === "Director" ? "Choose Employee" : "Employee"}
        </button>
        <button className="button" onClick={handleAllEmployeesClick}>All Employees</button>
      </div>

      {authorizationError && (
        <div>Access denied. You are not authorized to view this statistic.</div>
      )}

      {allEmployees.length > 0 && (
        <EmployeeList allEmployees={allEmployees} />
      )}

    </div>
  );
};

export default Statistic;
