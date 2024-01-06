import React from "react";
import './ArchiveItem.css';

const ArchiveItem = ({ id }) => {

    return (
        <div className="archive-item">
            <span>{id}</span>
        </div>
    );
}

export default ArchiveItem;