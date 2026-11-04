import { useEffect, useState } from "react";
import api from "../api";

function Events() {

    const [events, setEvents] = useState([]);

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

    const bookEvent = async (eventId) => {

        try {

            await api.post("/api/bookings", null, {
                params: {
                    eventId: eventId
                }
            });

            alert("Event booked successfully!");

        } catch (error) {

            console.log(error);
            console.log(error.response);

            alert(
                error.response?.data?.message ||
                "Booking failed!"
            );
        }
    };

    return (
        <div className="events-content">

            {events.length === 0 ? (

                <p>No events available.</p>

            ) : (

                <table className="events-table">

                    <thead>

                        <tr>
                            <th>Event Name</th>
                            <th>Date</th>
                            <th>Location</th>
                            <th>Details</th>
                            <th>Action</th>
                        </tr>

                    </thead>

                    <tbody>

                        {events.map((event) => (

                            <tr key={event.id}>

                                <td>{event.name}</td>

                                <td>{event.date}</td>

                                <td>{event.location}</td>

                                <td>{event.description}</td>

                                <td>

                                    <button
                                        className="book-button"
                                        onClick={() => bookEvent(event.id)}
                                    >
                                        Book Now
                                    </button>

                                </td>

                            </tr>

                        ))}

                    </tbody>

                </table>

            )}

        </div>
    );
}

export default Events;