import React, { useState } from "react";
import Modal from 'react-modal';
import './ImageDocument.css';
import { API } from "../api";

const ImageDocument = (props) => {

    const [modalOpen, setModalOpen] = useState(false);
    const [text, setText] = useState('');
    const [correct, setCorrect] = useState(false);
    const [incorrect, setIncorrect] = useState(false);
    const [revisors, setRevisors] = useState([]);
    const [sent, setSent] = useState(false);

    const userinfo = JSON.parse(sessionStorage.getItem("user"));

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
            width: '80%',
        },
      };

    async function openModal() {
        setModalOpen(true);
        console.log(props.imgdoc.documentUrl);
        try {
            const res = await fetch(props.imgdoc.documentUrl);
            const text = await res.text();
            console.log(text);
            setText(text);
        } catch(err) {
            console.log(err);
        }
        
        console.log(props.imgdoc);
    };
    
    function closeModal() {
        setModalOpen(false);
    };

    async function handleCorrectClick() {
        const data = {
            correct: true,
            id: props.imgdoc.documentId //valjda
        }
        try {
            const res = await API.post('/api/v1/document/correct', data, config);
            setCorrect(true);
            console.log(res);
        } catch(err) {
            console.log(err);
        }
        
    };

    async function handleIncorrectClick() {
        const data = {
            correct: false,
            id: props.imgdoc.documentId //valjda
        }
        try {
            const res = await API.post('/api/v1/document/correct', data, config);
            setIncorrect(true);
            console.log(res);
        } catch(err) {
            console.log(err);
        }
    };

    async function getRevisors() {
        try {
            const res = await API.get('/api/v1/employees/get-all-revisers', config)
            setRevisors(res.data);
            console.log(res);
        } catch(err) {
            console.log(err);
        }
    }

    async function handleRevisorClick(revisorid) {
        const data = {
            reviserId: revisorid,
            documentId: props.imgdoc.documentId
        }
        try {
            const res = await API.post('/api/v1/document/send-to-reviser', data, config);
            console.log(res);
            setSent(true);
        } catch(err) {
            console.log(err);
        }
    }

    return (
        <div className="img-doc-container">
            <img src={props.imgdoc.photoUrl} alt="img"
                onClick={openModal} className="document-photo"/>
            <Modal isOpen={modalOpen} onRequestClose={closeModal} style={customStyles}>
                <div className="modal-container">
                    <img src={props.imgdoc.photoUrl} alt="img"
                        className="modal-document-photo"/>
                    <p className="scanned-text">
                        {text}
                    </p>
                    { !correct && !incorrect ? (
                        <div>
                            <button onClick={handleCorrectClick}>Correct</button>
                            <button onClick={handleIncorrectClick}>Incorrect</button>
                        </div>
                    ): null }
                    {
                        correct && (
                        <div>
                            <p className="confirmation-text-correct">Scanned document has been confirmed as correct</p>
                            <button onClick={getRevisors}>Get revisors</button>
                        </div>
                        )
                    } 
                    {
                        incorrect && (
                        <div>
                            <p className="confirmation-text-incorrect">Scanned document is incorrect</p>
                        </div>
                        )
                    }
                    { revisors.length > 0 && !sent && (
                        <div>
                            <p>Revisors:</p>
                            {revisors.map((revisor, index) => (
                                <button key={index} onClick={() => handleRevisorClick(revisor.id)}> {revisor.firstName} {revisor.lastName} </button>
                            ))}
                        </div>
                        )
                    }
                    {
                        sent && (
                            <p className="confirmation-text">Document has been sent to revisor</p>
                        )
                    }            
                </div>
            </Modal>
        </div>
    );
};

export default ImageDocument;