(function() {
    'use strict';
    angular
        .module('weareholidaysApp')
        .factory('TripSummary', TripSummary);

    TripSummary.$inject = ['$resource'];

    function TripSummary ($resource) {
        var resourceUrl =  'api/trip-summaries/:id';

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
