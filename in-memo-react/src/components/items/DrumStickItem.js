import React from "react";
import { Button, ButtonGroup} from 'reactstrap';
import { Link } from 'react-router-dom';

function DrumStickItem(props) {
    const { drumstick, number } = props

    return (
        <tr key={drumstick.id}>
            <td><input 
                type="checkbox" 
                checked={drumstick.checked} 
                onClick={() => props.addCheckedItemToArray(drumstick.id)}/></td>
            <td>{number}</td>
            <td style={{whiteSpace: 'nowrap'}}>{drumstick.band}</td>
            <td>{drumstick.drummerName}</td>
            <td>{drumstick.date}</td>
            <td>{drumstick.city}</td>
            <td>{drumstick.description}</td>
            <td>
            <ButtonGroup>
                <Button size="sm" color="primary" tag={Link} to={"/drumsticks/" + drumstick.id}>Edit</Button>
                <Button size="sm" color="danger" onClick={() => props.removeItem(drumstick.id)}>Delete</Button>
            </ButtonGroup>
            </td>
        </tr>
    )
}

export default DrumStickItem