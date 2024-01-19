import React, { useState } from "react";
import Modal from 'react-modal';
import './HistoryItem.css';
import { API } from "../../api";

const HistoryItem = ({ id, name, doc }) => {

    const [modalOpen, setModalOpen] = useState(false);
    const [text, setText] = useState('');

    const userinfo = JSON.parse(sessionStorage.getItem("user"));

    const config = {
        headers: {
            Authorization: "Bearer " + userinfo.accessToken,
            "Access-Control-Allow-Origin": "*",
        },
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

    return (
        <div className="history-item">
            <button onClick={openModal} className="history-name-button">{ name }</button>
            <Modal isOpen={modalOpen} onRequestClose={closeModal} style={customStyles}>
                <div>
                    <p className="scanned-text">
                        { text }
                    </p>
                </div>
            </Modal>
        </div>
    );
}

export default HistoryItem;