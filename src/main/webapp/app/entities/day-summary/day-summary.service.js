(function() {
    'use strict';
    angular
        .module('weareholidaysApp')
        .factory('DaySummary', DaySummary);

    DaySummary.$inject = ['$resource'];

    function DaySummary ($resource) {
        var resourceUrl =  'api/day-summaries/:id';

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
