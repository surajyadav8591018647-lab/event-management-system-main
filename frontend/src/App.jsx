import { BrowserRouter, Routes, Route } from "react-router-dom";

import Home from "./pages/Home";
import Register from "./pages/Register";
import Login from "./pages/Login";
import Events from "./pages/Events";
import Admin from "./pages/Admin";
import UserDashboard from "./pages/UserDashboard";
import ProtectedRoute from "./components/ProtectedRoutes";
import RoleProtectedRoute from "./components/RoleProtectedRoute";

function App() {
    return (
        <BrowserRouter>

            <Routes>

                <Route path="/" element={<Home />} />

                <Route path="/register" element={<Register />} />

                <Route path="/login" element={<Login />} />

                <Route path="/events"element={<ProtectedRoute>  <Events /> </ProtectedRoute>}
/>

                <Route path="/admin" element={<RoleProtectedRoute requiredRole="ADMIN">
                     <Admin /> 
                </RoleProtectedRoute>} />

                <Route path="/dashboard" element={<ProtectedRoute>
                     <UserDashboard /> 
                     </ProtectedRoute>} />


            </Routes>

        </BrowserRouter>
    );
}

export default App;