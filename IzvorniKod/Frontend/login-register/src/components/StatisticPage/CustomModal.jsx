// import React, { useState } from "react";
// import Modal from 'react-modal';
// import axios from 'axios';

// const CustomModal = ({ isOpen, onRequestClose }) => {
//   const customStyles = {
//     content: {
//       top: '50%',
//       left: '50%',
//       right: 'auto',
//       bottom: 'auto',
//       marginRight: '-50%',
//       transform: 'translate(-50%, -50%)',
//     },
//   };

//   const [oldPassword, setOldPassword] = useState('');
//   const [newPassword, setNewPassword] = useState('');
//   const [passwordConfirmation, setPasswordConfirm] = useState('');

//   const handleSubmit = async (e) => {
//     e.preventDefault();

//     try {
//       const response = await axios.get('/api/v1/employee-management/all-employees');
//       const allEmployeeStatistics = response.data;

//       console.log('All Employee Statistics:', allEmployeeStatistics);

//       onRequestClose();
//     } catch (error) {
//       console.error('Error fetching employee statistics:', error);
//     }
//   };

//   return (
//     <Modal isOpen={isOpen} onRequestClose={onRequestClose} style={customStyles}>
//       <form onSubmit={handleSubmit}>
//         <button type="submit">Fetch All Employee Statistics</button>
//       </form>
//     </Modal>
//   );
// };

// export default CustomModal;
