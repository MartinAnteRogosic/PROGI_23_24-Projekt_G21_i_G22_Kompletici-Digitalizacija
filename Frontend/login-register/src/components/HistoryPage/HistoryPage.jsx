import React, { useEffect } from "react";
import './HistoryPage.css';
import { API } from "../../api";
import Header from "../Header/Header";

const HistoryPage = () => {

    useEffect(() => {
        getHistory();
    }, []);

    const userinfo = JSON.parse(sessionStorage.getItem("user"));

    const config = {
        headers: {
            Authorization: "Bearer " + userinfo.accessToken,
            "Access-Control-Allow-Origin": "*",
        },
    };
    

    async function getHistory() {
        try {
            const res = await API.get("/api/v1/document/document-history", config);
            console.log(res.data);
        } catch (err) {
          console.log(err);
        }
    }


    return (
    <div className="history-container">
        <Header />
        
    </div>
    );
}

export default HistoryPage;