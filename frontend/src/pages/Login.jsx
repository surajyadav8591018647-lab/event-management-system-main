import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../api";

function Login() {

    const [identifier, setIdentifier] = useState("");
    const [password, setPassword] = useState("");

    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();

        try {

            const response = await api.post("/api/users/login", {
                identifier: identifier,
                password: password
            });

            console.log(response.data);

            localStorage.setItem("token", response.data.token);
            localStorage.setItem("role", response.data.role);

            alert("Login successful!");

            if (response.data.role === "ADMIN") {
                navigate("/admin");
            } else {
                navigate("/dashboard");
            }

        } catch (error) {

            console.log(error);
            console.log(error.response);

            alert(
                error.response?.data?.message ||
                "Login failed Invalid Username or password"
            );
        }
    };

    return (
        <div className="auth-page">

            <div className="auth-box">

                <h1>Login</h1>

                <form onSubmit={handleLogin}>

                    <label>Username or Email</label>

                    <input
                        type="text"
                        value={identifier}
                        onChange={(e) => setIdentifier(e.target.value)}
                        required
                    />

                    <label>Password</label>

                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />

                    <button type="submit">
                        Login
                    </button>

                </form>

                <p>
                    Don't have an account?{" "}
                    <Link to="/register">
                        Signup
                    </Link>
                </p>

            </div>

        </div>
    );
}

export default Login;