module.exports = function(grunt) {
    grunt.loadNpmTasks('grunt-contrib-jshint');
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-jsdoc');
    grunt.loadNpmTasks('grunt-karma');
    grunt.loadNpmTasks('grunt-serve');

    grunt.initConfig({

        serve: {
            options: {
                port: 9000,
                'aliases': {
                    'assets/js/jar.min.js': {
                            tasks: ['concat','uglify'], // required
                            output: 'jar.min.js', // optional
                            contentType: 'text/javascript' // optional
                        }
                }
            },
           
        },

        'pkg': grunt.file.readJSON('package.json'),
        'jshint': {
            'beforeconcat': ['src/**/*.js'],
        },
        'copy': {
            '../text-analysis-service/src/main/webapp': {
                'files': [
                    // copy index.html
                    {
                        'expand': true,
                        'src': ['index.html'],
                        'dest': '../text-analysis-service/src/main/webapp/',
                        'filter': 'isFile',
                    },
                    // copy html template in views
                    {'expand': true, 'src': ['views/**'], 'dest': '../text-analysis-service/src/main/webapp/'},
                    {'expand': true, 'src': ['assets/**'], 'dest': '../text-analysis-service/src/main/webapp/'},
                ]
            },
            'libs': {
                'files': [
                    {'expand': true, 'src': ['node_modules/d3/dist/**'], 'dest': '../text-analysis-service/src/main/webapp/', 'filter': 'isFile'},
                    {'expand': true, 'src': ['node_modules/bootstrap/dist/**'], 'dest': '../text-analysis-service/src/main/webapp/', 'filter': 'isFile'},
                    {'expand': true, 'src': ['node_modules/angular/*.min.js*'], 'dest': '../text-analysis-service/src/main/webapp/', 'filter': 'isFile'},
                    {'expand': true, 'src': ['node_modules/angular-animate/*.min.js*'], 'dest': '../text-analysis-service/src/main/webapp/', 'filter': 'isFile'},
                    {'expand': true, 'src': ['node_modules/angular-bootstrap/*.min.js*'], 'dest': '../text-analysis-service/src/main/webapp/', 'filter': 'isFile'},
                    {'expand': true, 'src': ['node_modules/angular-route/*.min.js*'], 'dest': '../text-analysis-service/src/main/webapp/', 'filter': 'isFile'},
                    {'expand': true, 'src': ['node_modules/angular-touch/*.min.js*'], 'dest': '../text-analysis-service/src/main/webapp/', 'filter': 'isFile'},
                    {'expand': true, 'src': ['node_modules/angular-resource/*.min.js*'], 'dest': '../text-analysis-service/src/main/webapp/', 'filter': 'isFile'}
                    
                ]
            }
        },
        'concat': {
            'dist': {
                'src': ['src/**/*.js*'],
                'dest': '../text-analysis-service/src/main/webapp/assets/js/jar.js'
            },
            'dist': {
                'src': ['src/**/*.js*'],
                'dest': 'assets/js/jar.js'
            }
        },
        
        'uglify': {
            'options': {
                'mangle': false,
            },
            'assets/js': {
                'files': {
                    'assets/js/jar.min.js': ['assets/js/jar.js']
                }
            }
        },
        'jsdoc': {
            'src': ['src/**/*.js'],
            'options': {
                'destination': 'docs'
            }
        }
    });

    grunt.registerTask('build',
        [
            'jshint',
            'concat',
            'copy',
            'uglify',
            'jsdoc'
        ]);

    
};