import React from "react";

interface UploadResultProps {
    isMini:boolean
    list: any[]

}
const UploadResult = (props:UploadResultProps) => {
    const { list, isMini } = props;
    let results = list.map((r) =>
        (<div style={{color: r.success ? 'green' : 'red'}}>{r.image} {isMini ? '(mini)' : ''} : {r.success ? 'OK' : r.error}</div>));
    return (<div>{results}</div>)
}

export default UploadResult;