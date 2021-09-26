const link = "http://localhost:8080/";

export const postRequest = (uri, obj) => {
    fetch(link + uri, {
        method: 'post',
        credentials: 'include',
        headers: {
            'X-XSRF-TOKEN': getCsrf(uri),
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(obj)
    }).then(response => response)
        .catch(error => alert('Error:', error));
};

export const putRequest = (uri, obj) => {
    fetch(link + uri, {
        method: 'put',
        credentials: 'include',
        headers: {
            'X-XSRF-TOKEN': getCsrf(uri),
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(obj)
    }).then(response => response)
        .catch(error => alert('Error:', error));
};

export const deleteRequest = (uri) => {
    fetch(link + uri, {
        method: 'delete',
        credentials: 'include',
        headers: {
            'X-XSRF-TOKEN': getCsrf(uri),
            'Content-Type': 'application/json'
        }
    }).then(response => response)
        .catch(error => alert('Error:', error));
};

const getCsrf = (uri) => {
    fetch(link + uri);

    if (!document.cookie) {
        return '';
    }

    const xsrfCookies = document.cookie.split(';')
        .map(c => c.trim())
        .filter(c => c.startsWith('XSRF-TOKEN='));

    if (xsrfCookies.length === 0) {
        return '';
    }
    return decodeURIComponent(xsrfCookies[0].split('=')[1]);
};