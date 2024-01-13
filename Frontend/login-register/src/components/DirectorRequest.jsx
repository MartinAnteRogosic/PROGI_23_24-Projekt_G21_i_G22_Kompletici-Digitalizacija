import React, { useState } from "react";
import './RequestItem.css';
import { API } from "../api";
import Modal from 'react-modal';


const DirectorRequest = ({ id }) => {

    const [modalOpen, setModalOpen] = useState(false);

    const userinfo = JSON.parse(sessionStorage.getItem("user"));
    const user = {
        firstName: userinfo.firstName,
        lastName: userinfo.lastName,
        role: userinfo.role,
    };

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

    async function handleSigning() {
        try {
            //send request to backend to update signed to true
            const res = await API.post("/api/v1/document/sign-document", { documentId: id, confirm: true }, config);
            console.log(res);
        } catch (err) {
          console.log(err);
        }
    }

    async function handleShare() {
        try {
            //send request to backend to update signed to true
            const res = await API.post("/api/v1/document/shareOnFacebook", { documentId: id, confirm: true }, config);
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
                    <button>Sign document</button>
                    <button>Share on Facebook</button>
                </div>
            </Modal>
        </div>
    );

};

export default DirectorRequest;