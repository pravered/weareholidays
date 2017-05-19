(function() {
    'use strict';
    angular
        .module('weareholidaysApp')
        .factory('TripPeople', TripPeople);

    TripPeople.$inject = ['$resource'];

    function TripPeople ($resource) {
        var resourceUrl =  'api/trip-people/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
