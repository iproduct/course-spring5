/**
 * THIS HEADER SHOULD BE KEPT INTACT IN ALL CODE DERIVATIVES AND MODIFICATIONS.
 * 
 * This file provided by IPT is for non-commercial testing and evaluation
 * purposes only. IPT reserves all rights not expressly granted.
 *  
 * The security implementation provided is DEMO only and is NOT intended for production purposes.
 * It is exclusively your responsisbility to seek advice from security professionals 
 * in order to secure the REST API implementation properly.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * IPT BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Redirect, Route, RouteProps } from 'react-router-dom';

import { RootState } from '../../app/rootReducer';
import { requestedProtectedResource } from '../../features/auth/authSlice';


const ProtectedRoute: React.FC<RouteProps> = ({ children, ...rest }) => {
    const isAuthenticated = useSelector((state: RootState) => state.auth.loggedUser);
    const dispatch = useDispatch();

    return (
        <Route
            {...rest}
            render={({ location }) => {
                if (!isAuthenticated) {
                    dispatch(requestedProtectedResource(location.pathname))
                }

                return isAuthenticated ? (
                    children
                ) : (
                        <Redirect to={{ pathname: "/login" }} />
                    );
            }}
        />
    );
}

export default ProtectedRoute