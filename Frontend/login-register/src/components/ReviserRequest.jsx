import React, { useState } from "react";
import './RequestItem.css';
import { API } from "../api";
import Modal from 'react-modal';

const ReviserRequest = ({ id }) => {

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

    const config = {
        headers: {
            Authorization: "Bearer " + userinfo.accessToken,
            "Access-Control-Allow-Origin": "*",
        },
    }

    function openModal() { 
        setModalOpen(true);
    }

    function closeModal() { 
        setModalOpen(false);
    }

    async function handleVerify() {
        try {
            //send request to backend to update verified to true
            const res = await API.post("/api/v1/document/verify", { id: id, verified: true }, config);
            console.log(res);
        } catch (err) {
          console.log(err);
        }
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
                    <button>Verify correct scan</button>     
                </div>
            </Modal>
        </div>
    );

};

export default ReviserRequest;