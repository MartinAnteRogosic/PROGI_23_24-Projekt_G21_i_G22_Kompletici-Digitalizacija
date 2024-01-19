import React, { useState } from "react";
import './RequestItem.css';
import { API } from "../../api";
import Modal from 'react-modal';

const ReviserRequest = ({ id, name, photo, doc }) => {

    const [modalOpen, setModalOpen] = useState(false);
    const [correct, setCorrect] = useState(false);
    const [text, setText] = useState('');
    const [changeType, setChangeType] = useState(false);

    const customStyles = { 
        content: {
            top: '50%',
            left: '50%',
            right: 'auto',
            bottom: 'auto',
            marginRight: "-50%",
            transform: 'translate(-50%, -50%)',
            width: '80%'
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

    async function handleVerify() {
        try {
            //send request to backend to update verified to true
            const res = await API.post("/api/v1/document/verify", { id: id, verified: true }, config);
            console.log(res);
            setCorrect(true);
        } catch (err) {
          console.log(err);
        }
    }

    async function handleTypeChange(e) { 
        const data = {
            type: e.target.value,
            id: id
        }
        try {
            const res = await API.post('/api/v1/document/change-category', data, config);
            console.log(res);
            setChangeType(false);
        } catch(err) {
            console.log(err);
        }
    }

    return (
        <div className="request-item">
            <button className="request-name-button" onClick={openModal}>{ name }</button>
            <Modal isOpen={modalOpen} onRequestClose={closeModal} style={customStyles}>
                <div className="modal-container">
                    <img src={photo} alt="img"
                        className="modal-document-photo"/>
                    <p className="scanned-text">
                        { text }
                    </p>
                    <div>
                    {
                        correct ? 
                        <p className="confirmation-text">Verified</p>
                        :
                        (<button onClick={handleVerify}>Verify correct scan</button>

                        )
                    }
                    {
                        !correct && (
                            !changeType ? (
                                <button onClick={() => setChangeType(true)}>Change document type</button>
                            ) : (
                            <select onChange={handleTypeChange}>
                                <option value="RECEIPT">Receipt</option>
                                <option value="OFFER">Offer</option>
                                <option value="INTERNAL_DOCUMENT">Internal document</option>
                            </select>
                            )
                        )
                    }
                    </div>
                    
                </div>
            </Modal>
        </div>
    );

};

export default ReviserRequest;