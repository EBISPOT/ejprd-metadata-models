setup(
    name='ejprdmetadatapublisher',
    version='1.0.0',
    author='Olamidipupo Ajigboye',
    url='https://ebispot.github.io/ejprarediseases.github.io/',
    description='Publishes the current version of EJPRD metadata schema',
    licence='Apache 2.0',
    packages=['edit_models','metadata_releases'],
    install_requires=[''],
    entry_points=['console_scripts':[metadata = edit_models.app:run']
    ]

)
