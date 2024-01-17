import React, { useState } from "react";
import './RequestItem.css';
import { API } from "../../api";
import Modal from 'react-modal';

const AccountantRequest = ({ id, name, photo, doc }) => {

    const [modalOpen, setModalOpen] = useState(false);
    const [text, setText] = useState('');
    const [getSignature, setGetSignature] = useState(false);

    const userinfo = JSON.parse(sessionStorage.getItem("user"));
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

    
    const user = {
        firstName: userinfo.firstName,
        lastName: userinfo.lastName,
        role: userinfo.role,
    };

    async function openModal() { 
        setModalOpen(true);
        try {
            const res = await fetch(doc);
            const text = await res.text();
            console.log(text);
            setText(text);
        } catch(err) {
            console.log(err);
        }
        
        console.log(doc);
    }

    function closeModal() { 
        setModalOpen(false);
    }

    async function handleGetSignature() {
        try {
            const res = await API.post("/api/v1/document/send-to-sign", { id: id, toBeSigned: true }, config);
            console.log(res);
            setGetSignature(true);
        } catch (err) {
          console.log(err);
        }
    }

    async function handleArchiving() {
        try {
            const res = await API.post("/api/v1/archive/archive-document", { id: id, archived: true }, config);
            console.log(res);
            setGetSignature(true);
        } catch (err) {
          console.log(err);
        }
    }

    return (
        <div className="request-item">
            <span>{ name }</span>
            <button onClick={openModal}>Open</button>
            <Modal isOpen={modalOpen} onRequestClose={closeModal} style={customStyles}>
                <div>
                    <p className="scanned-text">
                        { text }
                    </p>
                    <button onClick={handleArchiving}>Archive document</button>
                    {
                        getSignature ? (
                            <p>Document has been sent for signature</p>
                        ) : (
                            <button onClick={handleGetSignature}>Get signature</button>
                        )
                    }
                    
                </div>
            </Modal>
        </div>
    );

};

export default AccountantRequest;