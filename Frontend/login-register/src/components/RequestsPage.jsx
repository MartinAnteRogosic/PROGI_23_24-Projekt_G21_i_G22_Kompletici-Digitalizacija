import React from "react";
import { API } from "../api";
import RequestItem from "./RequestItem";
import Header from "./Header";
import './RequestsPage.css';

const RequestsPage = () => {
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
    };

    async function getRequests() {
        try {
            //if logged-in user is revisor, send request for all documents and photos where verifierID == revisor's ID
            if (user.role === "revisor") {
                const res = await API.get("/api/v1/employees/get-revision-documents", config);
                console.log(res.data);
            }
            //if logged-in user is accountant, send request for all documents and photos where verified == true, archived == false and documentType == accountant's type
            //if logged-in user is director, send request for all documents and photos where signatureFromID == director's ID and signed == false
          const res = await API.get("", config);
        } catch (err) {
          console.log(err);
        }
      }

    return (
        <div className="requests-container">
            <Header />
            <button onClick={getRequests}>Refresh</button>
            <ul className="request-items">
                <li>
                    <RequestItem />
                </li>
            </ul>
        </div>
    );

};

export default RequestsPage;