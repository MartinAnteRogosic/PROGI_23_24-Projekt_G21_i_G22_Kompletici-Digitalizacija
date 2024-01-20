import React, { useState } from "react";
import { Link } from "react-router-dom";
import Modal from 'react-modal';
import './Header.css';
import { API } from "../../api";
import PasswordCheckList from "react-password-checklist";


const Header = () => {

  const userinfo = JSON.parse(sessionStorage.getItem("user"));

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
  const [employees, setEmployees] = useState([]);
  const [directorPass, setDirectorPass] = useState('');

  const config = {
    headers: {
        Authorization: "Bearer " + userinfo.accessToken,
        "Access-Control-Allow-Origin": "*",
        },
    };

  const [modalOpen, setModalOpen] = useState(false);
  const [deleteModalOpen, setDeleteModalOpen] = useState(false);
  
  const user = {
    "firstName": userinfo.firstName,
    "lastName": userinfo.lastName,
    "role": userinfo.role
  };

  function handleLogout() {
    sessionStorage.removeItem("user");
    window.location.reload();
  }

  async function openDeleteModal() {
    try {
      const res = await API.get('/api/v1/employee-management/all-employees', config);
      setEmployees(res.data);
      setDeleteModalOpen(true);
      console.log(res.data);
    } catch(err) {
      console.log(err);
    }
    

  }

  function closeDeleteModal() {
    setDeleteModalOpen(false);
  }

  function openModal() {
    setModalOpen(true);
  }

  function closeModal() {
    setModalOpen(false);
    setOldPassword('');
    setNewPassword('');
    setPasswordConfirm('');
  }

  async function handleSubmit(e) {
    e.preventDefault();
    const formData = new FormData(e.target);
    const formJSON = Object.fromEntries(formData.entries());
    //console.log(formJSON);
    try {
      const res = await API.patch("/api/v1/employees/changePassword", formJSON, config);
      //console.log(res);
      if (res.status == 202) {
        alert('Password successfully changed');
        closeModal();
      }
    } catch (err) {
      alert(err);
    }
  }

  async function handleDelete(id) {
    const data = {
      directorPassword: directorPass,
      employeeId: id
    }
    console.log(data);
    try {
      const res = await API.post('/api/v1/employee-management/delete-account', data, config);
      const updatedEmployees = employees.filter(employee => employee.id !== id);
      setEmployees(updatedEmployees);
      console.log(res);
    } catch(err) {
      alert("Pogrešna lozinka");
      console.log(err);
    }
  };

    return (
        <div className="header">
          <div className="user-info">
          <span className="username">NAME: { user.firstName + ' ' + user.lastName }</span>
          <span className="username">ROLE: { user.role }</span>
          <button onClick={handleLogout}>Log out</button>
          <button onClick={openModal}>Change password</button>
          {
            userinfo.role === "DIRECTOR" && (
              <button onClick={openDeleteModal}>Delete employees</button>
            )
          }
          <Modal isOpen={modalOpen} onRequestClose={closeModal} style={customStyles}>
            <form onSubmit={handleSubmit}>
              <label htmlFor="oldPassword">Old password: </label>
              <input value={oldPassword} onChange={(e) => setOldPassword(e.target.value)} type="password" name="oldPassword" id="oldPassword" required/>

              <label htmlFor="newPassword">New password: </label>
              <input value={newPassword} onChange={(e) => setNewPassword(e.target.value)} type="password" name="newPassword" id="newPassword" required/>

              <label htmlFor="passwordConfirmation">Confirm new password: </label>
              <input value={passwordConfirmation} onChange={(e) => setPasswordConfirm(e.target.value)} type="password" name="passwordConfirmation" id="passwordConfirmation" required/>

              <PasswordCheckList rules={["minLength", "specialChar", "number", "capital"]}
                                    minLength={6}
                                    value={newPassword}
                                    onChange={(isValid) => {}}/>

              <button type="submit">Change password</button>
            </form>
          </Modal>
          <Modal isOpen={deleteModalOpen} onRequestClose={closeDeleteModal} style={customStyles}>
            {
              employees.length > 0 && (
                <div className="employees-list">
                  {employees.map((item, index) => (
                    <div className="emp-buttons">
                      <span>{item.firstName + ' ' + item.lastName}</span>
                      <button onClick={() => handleDelete(item.id)}>Delete</button>
                    </div>    
                ))}
                </div>
              )
            }
            <label>Password:</label>
            <input value={directorPass} onChange={(e) => setDirectorPass(e.target.value)} type="password" required/>
          </Modal>

        </div>
        
        <div className="center-links">
          <Link to={`/home`}>Home</Link>
          <Link to={`/statistic`}>Statistic</Link>
          <Link to={`/requests`}>Requests</Link>
          <Link to={`/archive`}>Archive</Link>
          <Link to={`/history`}>History</Link>
        </div>

      </div>
    );
};

export default Header;