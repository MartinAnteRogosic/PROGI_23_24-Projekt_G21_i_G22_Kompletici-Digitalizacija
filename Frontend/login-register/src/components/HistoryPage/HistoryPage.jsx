import React, { useEffect, useState } from "react";
import './HistoryPage.css';
import { API } from "../../api";
import Header from "../Header/Header";
import HistoryItem from "./HistoryItem";

const HistoryPage = () => {

    useEffect(() => {
        getHistory();
    }, []);


    const [data, setData] = useState([]);
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
            marginRight: "-50%",
            transform: 'translate(-50%, -50%)',
        },
    }

    async function getHistory() {
        try {
            const res = await API.get("/api/v1/document/document-history", config);
            setData(res.data);
            console.log(res.data);
        } catch (err) {
          console.log(err);
        }
    }

    return (
    <div className="history-container">
        <Header />
        {   data.length > 0 ? (
                <ul className="history-items">
                {data.map((item, index) => (
                    <li key={item.documentId}>
                        <HistoryItem id={item.documentId} name={item.documentName} doc={item.documentUrl}/>
                    </li> 
                ))}
                </ul>) : (<div className="text-nothing"> User hasn't uploaded any documents yet </div>)
        }
    </div>
    );
}

export default HistoryPage;