import React from "react";
import './ArchiveItem.css';

const ArchiveItem = ({ id }) => {

    return (
        <div className="archive-item">
            <span>{id}</span>
            <button>Delete</button>
            <button>Share</button>
        </div>
    );
}

export default ArchiveItem;