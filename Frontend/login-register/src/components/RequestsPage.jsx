import React, { useContext, useEffect } from "react";
import { userContext } from "../userContext";

const showRequests = () => {

};

const RequestsPage = () => {
    useEffect(() => {
        const handleRequestRoute = () => {
            showRequests();
        }
    })
    const { user } = useContext(userContext);

    return (
        <div>
            <button onClick={showRequests}>Osvježi</button>
            <ul>
                <li>item 1
                    <button>Odobri</button>
                    <button>Share</button>
                    <button>Arhiviraj</button>
                    <button>Pošalji direktoru</button>
                    <button>Proslijedi računovođi</button>
                </li>
            </ul>
        </div>
    );

};

export default RequestsPage;