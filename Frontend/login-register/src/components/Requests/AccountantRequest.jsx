import React, { useState } from "react";
import './RequestItem.css';
import { API } from "../../api";
import Modal from 'react-modal';

const AccountantRequest = ({ id }) => {

    const [modalOpen, setModalOpen] = useState(false);

    const customStyles = { 
        content: {
            top: '50%',
            left: '50%',
            right: 'auto',
            bottom: 'auto',
            marginRight: "-50%",
            transform: 'translate(-50%, -50%)',
        },
    }

    const userinfo = JSON.parse(sessionStorage.getItem("user"));
    const user = {
        firstName: userinfo.firstName,
        lastName: userinfo.lastName,
        role: userinfo.role,
    };

    function openModal() { 
        setModalOpen(true);
    }

    function closeModal() { 
        setModalOpen(false);
    }

    return (
        <div className="request-item">
            <span>{ id }</span>
            <button onClick={openModal}>Open</button>
            <Modal isOpen={modalOpen} onRequestClose={closeModal} style={customStyles}>
                <div>
                    <img src="" alt="img"
                        className="modal-document-photo"/>
                    <p className="scanned-text">
                        { id }
                    </p>
                    <button>Archive document</button>
                    <button>Get signature</button>
                </div>
            </Modal>
        </div>
    );

};

export default AccountantRequest;