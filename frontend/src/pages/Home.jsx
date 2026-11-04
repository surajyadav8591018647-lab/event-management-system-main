import { Link } from "react-router-dom";

function Home() {

    return (
        <div className="home-page">

            <div className="home-content">

                <h1>Event Management System</h1>

                <p className="home-subtitle">
                    Discover and book exciting events easily.
                </p>

                <div className="home-buttons">

                    <Link to="/register">
                        <button>Register</button>
                    </Link>

                    <Link to="/login">
                        <button>Login</button>
                    </Link>

                </div>

                <div className="about-section">

                    <h2>About Event Management</h2>

                    <p>
                        Our Event Management System allows users
                        to view available events and book their
                        seats easily.
                    </p>

                    <p>
                        Administrators can create events and
                        view registered users.
                    </p>

                </div>

            </div>

        </div>
    );
}

export default Home;