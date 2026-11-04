import { useNavigate } from "react-router-dom";
import Events from "./Events";

function UserDashboard() {

    const navigate = useNavigate();

    const handleLogout = () => {

        localStorage.removeItem("token");
        localStorage.removeItem("role");

        navigate("/login");
    };

    return (
        <div className="user-dashboard">

            <div className="events-container">

                <h1>Available Events</h1>

                <Events />

                <button
                    className="logout-link"
                    onClick={handleLogout}
                >
                    Logout
                </button>

            </div>

        </div>
    );
}

export default UserDashboard;