import { Navigate } from "react-router-dom";

function RoleProtectedRoute( { children, requiredRole }){
    const token = localStorage.getItem("token");
    const role = localStorage.getItem("role");

    if (!token) {
        return <Navigate to="/login" replace />;
    }

    if (role !== requiredRole) {
        return <Navigate to="/login" replace />;
    }
    return children;
}

export default RoleProtectedRoute;