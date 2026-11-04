import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import api from "../api";

function Admin() {

    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [date, setDate] = useState("");
    const [location, setLocation] = useState("");

    const [events, setEvents] = useState([]);
    const [registeredUsers, setRegisteredUsers] = useState([]);
    const [selectedEvent, setSelectedEvent] = useState(null);

    useEffect(() => {
        loadEvents();
    }, []);

    const loadEvents = async () => {

        try {

            const response = await api.get("/api/events");

            setEvents(response.data);

        } catch (error) {

            console.log(error);
            alert("Failed to load events");

        }
    };

    const createEvent = async (e) => {

        e.preventDefault();

        try {

            await api.post("/api/events", {
                name: name,
                description: description,
                date: date,
                location: location
            });

            alert("Event created successfully!");

            setName("");
            setDescription("");
            setDate("");
            setLocation("");

            loadEvents();

        } catch (error) {

            console.log(error);
            console.log(error.response);

            alert(
                error.response?.data?.message ||
                "Failed to create event"
            );
        }
    };

    const cancelCreate = () => {

        setName("");
        setDescription("");
        setDate("");
        setLocation("");
    };

    const viewUsers = async (event) => {

        try {

            const response = await api.get(
                `/api/bookings/event/${event.id}/users`
            );

            setRegisteredUsers(response.data);
            setSelectedEvent(event);

        } catch (error) {

            console.log(error);
            console.log(error.response);

            alert("Failed to load registered users");

        }
    };

    const handleLogout = () => {

        localStorage.removeItem("token");
        localStorage.removeItem("role");

        window.location.href = "/login";
    };

    return (
        <div className="admin-page">

            {/* CREATE EVENT */}

            <div className="admin-container">

                <h1>Create New Event</h1>

                <form onSubmit={createEvent}>

                    <label>Event Name</label>

                    <input
                        type="text"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        required
                    />

                    <label>Event Date</label>

                    <input
                        type="date"
                        value={date}
                        onChange={(e) => setDate(e.target.value)}
                        required
                    />

                    <label>Event Location</label>

                    <input
                        type="text"
                        value={location}
                        onChange={(e) => setLocation(e.target.value)}
                        required
                    />

                    <label>Event Details</label>

                    <textarea
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                        required
                    />

                    <button
                        type="submit"
                        className="admin-button"
                    >
                        Create Event
                    </button>

                </form>

                <Link
                    to="/admin"
                    className="cancel-link"
                    onClick={cancelCreate}
                >
                    Cancel
                </Link>

            </div>


            {/* EVENT MANAGEMENT */}

            <div className="admin-container">

                <h1>Event Management</h1>

                <table className="admin-events-table">

                    <thead>

                        <tr>
                            <th>Event Name</th>
                            <th>Date</th>
                            <th>Location</th>
                            <th>View Users</th>
                        </tr>

                    </thead>

                    <tbody>

                        {events.map((event) => (

                            <tr key={event.id}>

                                <td>{event.name}</td>

                                <td>{event.date}</td>

                                <td>{event.location}</td>

                                <td>

                                    <button
                                        className="admin-button"
                                        onClick={() => viewUsers(event)}
                                    >
                                        View Users
                                    </button>

                                </td>

                            </tr>

                        ))}

                    </tbody>

                </table>

            </div>


            {/* REGISTERED USERS */}

            {selectedEvent && (

                <div className="admin-container">

                    <h2>
                        Users Registered for {selectedEvent.name}
                    </h2>

                    {registeredUsers.length === 0 ? (

                        <p className="no-users">
                            No users registered.
                        </p>

                    ) : (

                        <div className="users-list">

                            {registeredUsers.map((user) => (

                                <div
                                    className="user-box"
                                    key={user.id}
                                >
                                    {user.name}
                                </div>

                            ))}

                        </div>

                    )}

                </div>

            )}


            {/* LOGOUT */}

            <div className="admin-logout">

                <button
                    onClick={handleLogout}
                    className="logout-link"
                >
                    Logout
                </button>

            </div>

        </div>
    );
}

export default Admin;