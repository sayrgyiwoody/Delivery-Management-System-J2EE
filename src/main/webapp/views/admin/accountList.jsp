<%@ include file="../../layout/header.jsp" %>
<%@ include file="../../layout/admin_sidenav.jsp" %>
<%@ page import="model.User" %>
<%
Object userObj = session.getAttribute("user");
if (userObj == null) {
    response.sendRedirect("/DMS/views/login.jsp");
} else if (userObj == null || !(userObj instanceof User)) {
    response.sendRedirect("/DMS/views/login.jsp");
} else {
    User user = (User) userObj;
    if (!"admin".equals(user.getRole())) {
        response.sendRedirect("/DMS/views/login.jsp");
    }
}
%>
<!-- Main content -->
<div class="p-5 md:p-10  sm:ml-64">
<input type="hidden" id="status" value="<%= request.getAttribute("status") %>" />
<div class="flex flex-column sm:flex-row flex-wrap space-y-4 sm:space-y-0 items-center justify-between pb-4">
        <a href="accountList?role=${role}" class="py-2.5 px-5 me-2 mb-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-100 dark:focus:ring-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700">Show All
        </a>
    
        <form action="accountList" method="get" class="relative">
            <div class="absolute inset-y-0 left-0 rtl:inset-r-0 rtl:right-0 flex items-center ps-3 pointer-events-none">
                <svg class="w-5 h-5 text-gray-500 dark:text-gray-400" aria-hidden="true" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z" clip-rule="evenodd"></path></svg>
            </div>
            <input type="hidden" name="role" value="${role}" >
            <input name="searchKey" value="${searchKey}" type="text" id="table-search" class="block p-2 ps-10 text-sm text-gray-900 border border-gray-300 rounded-lg w-80 bg-gray-50 focus:ring-blue-500 focus:border-blue-500 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Search for account">
        </form>
        </div>
<div class="relative overflow-x-auto shadow-md sm:rounded-lg">
    <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400">
        <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
            <tr>
                <th scope="col" class="px-6 py-3">
                    User ID
                </th>
                <th scope="col" class="px-6 py-3">
                    Name
                </th>
                <th scope="col" class="px-6 py-3">
                    Email
                </th>
                <th scope="col" class="px-6 py-3">
                    Phone Number                </th>
                <th scope="col" class="px-6 py-3">
                    Address
                </th>
                <th scope="col" class="px-6 py-3">
                    Action
                </th>
            </tr>
        </thead>
        <tbody>
        <c:forEach items="${accountList}" var="acc">
            <tr class="odd:bg-white odd:dark:bg-gray-900 even:bg-gray-50 even:dark:bg-gray-800 border-b dark:border-gray-700">
                <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                    ${acc.id}
                </th>
                <td class="px-6 py-4">
                    ${acc.name}
                </td>
                <td class="px-6 py-4">
                    ${acc.email}
                </td>
                <td class="px-6 py-4">
                    ${acc.phone}
                </td>
                <td class="px-6 py-4">
                    ${acc.address}
                </td>
                <td class="px-6 py-4">
                <c:if test="${acc.id != sessionScope.user.id}">
                
                    <form action="accountList" method="post">
                	<input type="hidden" value="${acc.id}" name="acc_id">
                	<input type="hidden" value="${acc.role }" name="acc_role">
                	<input type="hidden" value="delete" name="action">
                    <button type="submit" class="font-medium text-red-600 dark:text-red-500 hover:underline">delete</button>
                </form>
                </c:if>
                </td>
            </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
</div>

<script>

	const status = document.getElementById("status").value;
	if(status == "deleteSuccess"){
		swal("Deleted","Account deleted successfully","success");
	}
	if(status == "fail"){
		swal("Sorry","Account delete fail","error");
	}

</script>

<%@ include file="../../layout/footer.jsp" %>





