(function() {
    'use strict';
    angular
        .module('weareholidaysApp')
        .factory('TripSettings', TripSettings);

    TripSettings.$inject = ['$resource'];

    function TripSettings ($resource) {
        var resourceUrl =  'api/trip-settings/:id';

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
