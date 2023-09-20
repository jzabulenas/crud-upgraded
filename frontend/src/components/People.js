import { useEffect, useState } from 'react';
import "../style.scss";
import "./People.scss";
import PersonUpdateForm from './PersonUpdateForm';

export default function People() {
    const [people, setPeople] = useState([]);
    const [updatingPerson, setUpdatingPerson] = useState(null);
    const [isDeleted, setIsDeleted] = useState(false);

    const handleUpdateClick = (person) => {
        setUpdatingPerson(person);
    };

    const handleFormSubmit = async (updatedPerson) => {
        // Perform PUT request to update user data in the backend
        // Once the update is successful, update local state
        // and hide the form (setEditingUser(null))

        const requestOptions = {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ ...updatedPerson })
        };

        const response = await fetch(`http://localhost:8080/people/${updatedPerson.id}`, requestOptions);
        setUpdatingPerson(null);
    };

    const handlePersonDelete = async (personId) => {
        const requestOptions = {
            method: 'DELETE',
            headers: { 'Content-Type': 'application/json' }
        };

        const response = await fetch(`http://localhost:8080/people/${personId}`, requestOptions);
        setIsDeleted(!isDeleted);
    };

    useEffect(() => {
        let active = true;

        const fetchData = async () => {
            const response = await fetch(`http://localhost:8080/people`);
            const data = await response.json();

            if (active) {
                setPeople(data);
            }
        };

        fetchData();
        return () => {
            active = false;
        };
    }, [updatingPerson, isDeleted]);

    return (
        <article className='container'>
            <table className='table responsive'>
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>First name</th>
                        <th>Last name</th>
                        <th>Birthday</th>
                        <th>Address</th>
                        <th>City</th>
                        <th>State</th>
                        <th>Zip code</th>
                        <th>Phone number</th>
                        <th>Update</th>
                    </tr>
                </thead>

                <tbody>
                    {people.map(person => {
                        return (
                            <tr key={person.id}>
                                <td data-label="id">{person.id}</td>
                                <td data-label="First name">{person.firstName}</td>
                                <td data-label="Last name">{person.lastName}</td>
                                <td data-label="Birthday">{person.birthday}</td>
                                <td data-label="Address">{person.address}</td>
                                <td data-label="City">{person.city}</td>
                                <td data-label="State">{person.state}</td>
                                <td data-label="Zip code">{person.zipCode}</td>
                                <td data-label="Phone number">{person.phoneNumber}</td>
                                <td data-label="Update"><button onClick={() => handleUpdateClick(person)}>Update</button></td>
                                <td data-label="Delete"><button onClick={() => handlePersonDelete(person.id)}>Delete</button></td>
                            </tr>
                        );
                    })}
                </tbody>
            </table>
            {updatingPerson && (
                <PersonUpdateForm
                    person={updatingPerson}
                    onSubmit={handleFormSubmit}
                    onCancel={() => setUpdatingPerson(null)}
                />
            )}
        </article >
    );
};