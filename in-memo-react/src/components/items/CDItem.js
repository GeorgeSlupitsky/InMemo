import React from "react";
import { Button, ButtonGroup } from 'reactstrap';
import { Link } from 'react-router-dom';

function CDItem(props) {
    const { cd, number } = props

    let bandMembers = ''
    if (cd.band.bandMembers !== null) {
        bandMembers = cd.band.bandMembers.map(bandMember => {
            let member = bandMember.name
            return <p key={bandMember.name}>{member}</p>
        })
    }

    return (
        <tr key={cd.id}>
            <td>{number}</td>
            <td style={{ whiteSpace: 'nowrap' }}>{cd.band.name}</td>
            <td>{cd.album}</td>
            <td>{cd.year}</td>
            <td>{cd.booklet}</td>
            <td>{cd.countryEdition}</td>
            <td>{cd.cdType}</td>
            <td>{bandMembers}</td>
            <td>
                <ButtonGroup>
                    <Button size="sm" color="primary" tag={Link} to={props.editLink + cd.id}>Edit</Button>
                    <Button size="sm" color="danger" onClick={() => props.removeItem(cd.id)}>Delete</Button>
                </ButtonGroup>
            </td>
        </tr>
    )
}


export default CDItem