import React from "react"
import { Button, ButtonGroup } from 'reactstrap'
import { Link } from 'react-router-dom'
import { FormattedMessage } from 'react-intl'

function DrumStickItem(props) {
    const { drumstick, number } = props

    return (
        <tr key={drumstick.id}>
            <td><input
                type="checkbox"
                checked={drumstick.checked}
                onClick={() => props.addCheckedItemToArray(drumstick.id)} /></td>
            <td>{number}</td>
            <td style={{ whiteSpace: 'nowrap' }}>{drumstick.band}</td>
            <td>{drumstick.drummerName}</td>
            <td>{drumstick.date}</td>
            <td>{drumstick.city}</td>
            <td>{drumstick.description}</td>
            <td>
                <ButtonGroup>
                    <Button size="sm" color="primary" tag={Link} to={"/drumsticks/" + drumstick.id}>
                        <FormattedMessage id="Item.edit" defaultMessage="Edit" />
                    </Button>
                    <Button size="sm" color="danger" onClick={() => props.removeItem(props.service, props.deleteURL, drumstick.id)}>
                        <FormattedMessage id="Item.delete" defaultMessage="Delete" />
                    </Button>
                </ButtonGroup>
            </td>
        </tr>
    )
}

export default DrumStickItem